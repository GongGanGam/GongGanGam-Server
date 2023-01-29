package site.gonggangam.gonggangam_server.domain.users;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import site.gonggangam.gonggangam_server.domain.users.types.*;
import site.gonggangam.gonggangam_server.domain.repository.UserSettingsRepository;
import site.gonggangam.gonggangam_server.domain.repository.UsersRepository;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class UsersTest {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    private static final String nickname = "홍길동";
    private static final Integer birthYear = 1999;
    private static final String email = "testemail@naver.com";
    private static final GenderType gender = GenderType.UNKNOWN;

    @Test
    @DisplayName("생성한 사용자가 저장되는지 확인")
    public void test1_userSaveTest() {
        Users user = Users.builder()
                .nickname(nickname)
                .birthYear(birthYear)
                .email(email)
                .role(Role.USER)
                .userStatus(UserStatus.NORMAL)
                .genderType(gender)
                .provider(ProviderType.APPLE)
                .build();

        UserSettings settings = UserSettings.builder()
                .user(user)
                .notifyChat(true)
                .notifyDiary(true)
                .notifyReply(true)
                .shareType(ShareType.ALL)
                .build();

        userSettingsRepository.save(settings);
    }

    @Test
    @DisplayName("저장한 사용자를 잘 조회하는지 확인")
    public void test2_loadUserTest() {
        Users user = userRepository.findById(1L).orElseThrow();

        assertEquals(user.getNickname(), nickname);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getGenderType(), gender);
        assertEquals(user.getBirthYear(), birthYear);
    }

    @Test
    @DisplayName("저장된 사용자 설정 정보를 잘 조회하는지 확인")
    @Transactional
    public void test3_loadUserSettings() {
        Users user = userRepository.findById(1L).orElseThrow();
        UserSettings settings = user.getSettings();

        assertEquals(settings.getNotifyChat(), true);
        assertEquals(settings.getNotifyDiary(), true);
        assertEquals(settings.getNotifyReply(), true);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void userDataIntegrityViolationExceptionTest() {
        userRepository.save(Users.builder().build());
    }

}
