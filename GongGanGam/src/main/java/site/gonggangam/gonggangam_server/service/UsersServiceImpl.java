package site.gonggangam.gonggangam_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.domain.users.UserSettings;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.dto.auth.OAuthRequestDto;
import site.gonggangam.gonggangam_server.dto.auth.OAuthResponseDto;
import site.gonggangam.gonggangam_server.dto.users.UserSettingsRequestDto;
import site.gonggangam.gonggangam_server.dto.users.UsersRequestDto;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.dto.users.UsersResponseDto;
import site.gonggangam.gonggangam_server.repository.UserInfoRepository;
import site.gonggangam.gonggangam_server.repository.UserSettingsRepository;
import site.gonggangam.gonggangam_server.repository.UsersRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserSettingsRepository userSettingsRepository;

    @Override
    @Transactional
    public UsersResponseDto createUser(UsersRequestDto.PostUser request, Long userId) throws GeneralException {
//        Users user = usersRepository.findById(userId).orElseThrow(() -> {
//            throw new GeneralException(ResponseCode.NOT_FOUND_USER);
//        });
//
//        user.update(request.getNickname(), Integer.parseInt(request.getBirthYear()), request.getGender());
//        usersRepository.save(user);
//
//        return UsersResponseDto.toDto(user, user.getSettings());
        return null;
    }

    @Override
    public UsersResponseDto loadUser(String email) throws GeneralException {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ResponseCode.NOT_FOUND_USER));

        return UsersResponseDto.of(user, user.getSettings());
    }

    @Override
    @Transactional
    public void updateInfo(Long userId, UsersRequestDto.PutUserInfo request) throws GeneralException {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ResponseCode.NOT_FOUND_USER));

        user.getUserInfo().update(request.getNickname(),
                Integer.parseInt(request.getBirthYear()),
                request.getGender()
                );
        user.getSettings()
                .updateShareType(request.getShareType());

        usersRepository.save(user);
    }

//    @Override
    public void updateSettings(Long userId, UserSettingsRequestDto request) throws GeneralException {
        UserSettings settings = userSettingsRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ResponseCode.NOT_FOUND_USER));

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

}
