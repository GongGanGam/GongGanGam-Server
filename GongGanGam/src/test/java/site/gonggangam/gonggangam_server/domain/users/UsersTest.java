package site.gonggangam.gonggangam_server.domain.users;

import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import site.gonggangam.gonggangam_server.domain.users.types.AuthType;
import site.gonggangam.gonggangam_server.domain.users.types.GenderType;
import site.gonggangam.gonggangam_server.domain.users.types.ShareType;
import site.gonggangam.gonggangam_server.repository.UserSettingsRepository;
import site.gonggangam.gonggangam_server.repository.UsersRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersTest {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("생성한 사용자가 저장되는지 확인")
    public void userSaveAndLoadTest() {
        String nickname = "홍길동";
        Integer birthYear = 1999;
        String email = "testemail@naver.com";
        GenderType gender = GenderType.UNKNOWN;

//        Users user = Users.builder()
//                .nickname(nickname)
//                .birthYear(birthYear)
//                .email(email)
//                .genderType(gender)
//                .authType(AuthType.APPLE)
//                .activeStatus(ActiveStatus.ACTIVE)
//                .build();
//
//        UserSettings settings = UserSettings.builder()
////                .userId(user.getUserId())
//                .user(user)
//                .notifyChat(true)
//                .notifyDiary(true)
//                .notifyDiary(true)
//                .shareType(ShareType.DEFAULT)
//                .build();

//        userRepository.save(user);
//        userSettingsRepository.save(settings);

        System.out.println("settings : " + userSettingsRepository.findAll());
    }

    @Test
    @DisplayName("저장한 사용자를 잘 조회하는지 확인")
    public void loadUserTest() {

    }



    @Test(expected = DataIntegrityViolationException.class)
    public void userDataIntegrityViolationExceptionTest() {
        userRepository.save(Users.builder().build());
    }

    @Test
    public void baseTimeTest() {
    }

}
