package site.gonggangam.gonggangam_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.domain.users.UserSettings;
import site.gonggangam.gonggangam_server.domain.users.types.*;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.dto.auth.OAuthRequestDto;
import site.gonggangam.gonggangam_server.dto.auth.OAuthResponseDto;
import site.gonggangam.gonggangam_server.dto.users.UserSettingsRequestDto;
import site.gonggangam.gonggangam_server.dto.users.UsersRequestDto;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.dto.users.UsersResponseDto;
import site.gonggangam.gonggangam_server.repository.UserSettingsRepository;
import site.gonggangam.gonggangam_server.repository.UsersRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UserSettingsRepository userSettingsRepository;

    @Override
    @Transactional
    public UsersResponseDto createUser(UsersRequestDto.PostUser request, String email, ProviderType provider, Role role) throws GeneralException{
        Users newUser = Users.builder()
                .nickname(request.getNickname())
                .genderType(GenderType.valueOf(request.getGender()))
                .birthYear(Integer.parseInt(request.getBirthYear()))
                .role(role)
                .userStatus(UserStatus.NORMAL)
                .email(email)
                .provider(provider)
                .build();

        UserSettings settings = createDefaultSettings(newUser);
        userSettingsRepository.save(settings);

        return UsersResponseDto.toDto(newUser, settings);
    }

    @Override
    public UsersResponseDto loadUser(String email) throws GeneralException {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new GeneralException(ResponseCode.NOT_FOUND_USER);
                });

        return UsersResponseDto.toDto(user, user.getSettings());
    }

    @Override
    @Transactional
    public void updateInfo(Long userId, UsersRequestDto.PutUserInfo request) {
        Users user = usersRepository.findById(userId).orElseThrow();
        user.update(request.getNickname(),
                Integer.parseInt(request.getBirthYear()),
                GenderType.valueOf(request.getGender())
                );
        user.getSettings()
                .updateShareType(ShareType.valueOf(request.getShareType()));

        usersRepository.save(user);
    }

//    @Override
    public void updateSettings(Long userId, UserSettingsRequestDto request) {
        UserSettings settings = userSettingsRepository.findById(userId)
                .orElseThrow();

        settings.updateNotify(
                request.getNotifyDiary(),
                request.getNotifyReply(),
                request.getNotifyChat()
        );

        userSettingsRepository.save(settings);
    }

//    @Override
    public OAuthResponseDto oauthLogin(OAuthRequestDto request) {
        return null;
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

}
