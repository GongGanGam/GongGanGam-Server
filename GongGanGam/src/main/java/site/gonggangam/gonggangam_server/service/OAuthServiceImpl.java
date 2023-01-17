package site.gonggangam.gonggangam_server.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.config.auth.JwtProvider;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.domain.oauth.KakaoUserInfo;
import site.gonggangam.gonggangam_server.domain.users.UserSettings;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.types.*;
import site.gonggangam.gonggangam_server.dto.auth.OAuthRequestDto;
import site.gonggangam.gonggangam_server.dto.auth.OAuthResponseDto;
import site.gonggangam.gonggangam_server.repository.UserSettingsRepository;
import site.gonggangam.gonggangam_server.repository.UsersRepository;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OAuthServiceImpl implements OAuthService {

    private final JwtProvider jwtProvider;
    private final UsersRepository usersRepository;
    private final UserSettingsRepository userSettingsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username)
                .orElseThrow(() -> {
                    throw new GeneralException(ResponseCode.NOT_FOUND_USER);
                });
    }

    @Override
    @Transactional
    public OAuthResponseDto kakaoLogin(OAuthRequestDto request) throws GeneralException {
        String email = jwtProvider.getEmailFromToken(request.getToken());
        UserDetails account = loadUserByUsername(email);

        String accessToken = jwtProvider.generateAccessToken(account);
        String refreshToken = jwtProvider.generateRefreshToken(account);
        return OAuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Map<String, Object> getUserAttributesByToken(String requestUri, String token) {
        return WebClient.create()
                .get()
                .uri(requestUri)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }

    private Users getUserProfileByToken(Provider provider, String token) throws IllegalArgumentException {
        if (provider == Provider.KAKAO) {
            Map<String, Object> userAttributes = getUserAttributesByToken(provider.OAUTH_URI, token);
            KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(userAttributes);
            Long kakaoId = kakaoUserInfo.getId();
            String email = kakaoUserInfo.getEmail();
            String nickname = kakaoUserInfo.getNickname();
            Optional<Users> user = usersRepository.findByEmail(email);

            if (user.isPresent()) {
                return user.get();
            } else {

                Users newUser = Users.builder()
                        .role(Role.USER)
                        .userStatus(UserStatus.NORMAL)
                        .provider(ProviderType.KAKAO)
                        .build();

                UserSettings settings = createDefaultSettings(newUser);
                userSettingsRepository.save(settings);
            }
        }
        throw new IllegalArgumentException("잘못된 provider입니다.");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Users user = (Users) loadUserByUsername(authentication.getName());

        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

    private UserSettings createDefaultSettings(Users user) {
        return UserSettings.builder()
                .user(user)
                .notifyChat(true)
                .notifyDiary(true)
                .notifyReply(true)
                .shareType(ShareType.ALL)
                .build();
    }

    @Getter
    @AllArgsConstructor
    private static enum Provider {
        KAKAO("https://kapi.kakao.com/v2/user/me");

        private final String OAUTH_URI;
    }
}
