package site.gonggangam.gonggangam_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.gonggangam.gonggangam_server.domain.users.types.AuthType;
import site.gonggangam.gonggangam_server.dto.users.UsersRequestDto;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.repository.UsersRepository;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
//    private final UserSettingsRepository userSettingsRepository;

    @Override
    public Users create(UsersRequestDto.Post request, String email, AuthType authType) {
        Users newUser = Users.builder()
//                .birthYear(Integer.parseInt(request.getBirthYear()))
//                .genderType(request.getGender())
//                .nickname(request.getNickname())
//                .email(email)
//                .authType(authType)
//                .activeStatus(ActiveStatus.ACTIVE)
                .build();

//        UserSettings settings = createDefaultSettings(newUser);

        usersRepository.save(newUser);
//        userSettingsRepository.save(settings);

        return newUser;
    }

    @Override
    public void updateInfo(Long userId, UsersRequestDto.PutInfo request) {

    }


//    private UserSettings createDefaultSettings(Users user) {
//        return UserSettings.builder()
//                .user(user)
//                .notifyChat(true)
//                .notifyDiary(true)
//                .notifyReply(true)
//                .shareType(ShareType.DEFAULT)
//                .build();
//    }
}
