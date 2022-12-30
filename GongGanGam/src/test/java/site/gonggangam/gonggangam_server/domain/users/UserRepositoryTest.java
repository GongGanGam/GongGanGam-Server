package site.gonggangam.gonggangam_server.domain.users;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import site.gonggangam.gonggangam_server.repository.UsersRepository;

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
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void userDataIntegrityViolationExceptionTest() {
        userRepository.save(Users.builder().build());
    }

    @Test
    public void baseTimeTest() {
    }

}
