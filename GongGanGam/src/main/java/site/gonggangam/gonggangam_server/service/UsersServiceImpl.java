package site.gonggangam.gonggangam_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.gonggangam.gonggangam_server.config.exceptions.GeneralException;
import site.gonggangam.gonggangam_server.domain.users.UserSettings;
import site.gonggangam.gonggangam_server.domain.users.types.*;
import site.gonggangam.gonggangam_server.config.ResponseCode;
import site.gonggangam.gonggangam_server.dto.users.UserSettingsRequestDto;
import site.gonggangam.gonggangam_server.dto.users.UsersRequestDto;
import site.gonggangam.gonggangam_server.domain.users.Users;
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
    public Users createUser(UsersRequestDto.PostUser request, String email, ProviderType provider, Role role) {
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
        return newUser;
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

    @Override
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


    private UserSettings createDefaultSettings(Users user) {
        return UserSettings.builder()
                .user(user)
                .notifyChat(true)
                .notifyDiary(true)
                .notifyReply(true)
                .shareType(ShareType.ALL)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username)
                .orElseThrow(() -> {
                    throw new GeneralException(ResponseCode.NOT_FOUND_USER);
                });
    }
}
