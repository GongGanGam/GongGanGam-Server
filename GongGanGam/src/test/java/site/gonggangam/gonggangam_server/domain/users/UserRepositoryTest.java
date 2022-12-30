package site.gonggangam.gonggangam_server.domain.users;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
<<<<<<< Updated upstream
=======
import site.gonggangam.gonggangam_server.domain.ActiveStatus;
import site.gonggangam.gonggangam_server.domain.users.types.AuthType;
import site.gonggangam.gonggangam_server.domain.users.types.Gender;
import site.gonggangam.gonggangam_server.domain.users.types.ShareType;
>>>>>>> Stashed changes

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UsersRepository userRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void userSaveAndLoadTest() {
        String nickname = "홍길동";
        int birthYear = 96;
        char gender = 'M';
        String email = "testemail@naver.com";
        String type = "NAVER";
        String identification = "test_identification";
        char setAge = 'F';
        String deviceToken = "test_device_token";
<<<<<<< Updated upstream
        String status = "ACTIVE";
=======
        ActiveStatus activeStatus = ActiveStatus.ACTIVE;
>>>>>>> Stashed changes

        userRepository.save(Users.builder()
                .nickname(nickname)
                .birthYear(birthYear)
                .gender(gender)
                .email(email)
                .type(type)
                .identification(identification)
                .setAge(setAge)
                .deviceToken(deviceToken)
                .status(activeStatus)
                .build());

        List<Users> userList = userRepository.findAll();

        Users user = userList.get(0);
        assertEquals(user.getNickname(), nickname);
        assertEquals(user.getBirthYear(), birthYear);
        assertEquals(user.getGender(), gender);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getType(), type);
        assertEquals(user.getIdentification(), identification);
        assertEquals(user.getSetAge(), setAge);
        assertEquals(user.getDeviceToken(), deviceToken);
        assertEquals(user.getActiveStatus(), activeStatus);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void userDataIntegrityViolationExceptionTest() {
        userRepository.save(Users.builder().build());
    }

    @Test
    public void baseTimeTest() {
        final LocalDateTime now = LocalDateTime.now();
        userRepository.save(Users.builder()
                .birthYear(82)
                .email("test@naver.com")
                .gender('F')
                .deviceToken("device_token")
                .identification("ident")
                .nickname("nickname_test")
<<<<<<< Updated upstream
                .setAge('T')
                .status("ACTIVE")
                .type("NAVER")
=======
                .shareType(ShareType.SIMILAR_AGE)
                .status(ActiveStatus.ACTIVE)
                .authType(AuthType.NAVER)
>>>>>>> Stashed changes
                .build());

        List<Users> usersList = userRepository.findAll();
        Users users = usersList.get(0);

        assertTrue(users.getCreatedAt().isAfter(now));
        assertTrue(users.getUpdatedAt().isAfter(now));
    }

}
