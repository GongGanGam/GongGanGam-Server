package site.gonggangam.gonggangam_server.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.gonggangam.gonggangam_server.dto.users.PatchUsersRequestDto;
import site.gonggangam.gonggangam_server.domain.users.Users;
import site.gonggangam.gonggangam_server.domain.users.UsersRepository;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersService usersService;

    @After
    public void cleanup() {
        usersRepository.deleteAll();
    }

    @Test
    public void usersModifyTest() {
        Users user = usersRepository.save(getDummyUsers());

        String nickname = "new_nickname";
        int birthYear = 90;
        char setAge = 'T';
        char gender = 'F';

        usersService.update(user.getUserIdx(), new PatchUsersRequestDto(
                nickname, birthYear, setAge, gender
        ));

        Users modifiedUser = usersRepository.findAll().get(0);

        // modified info
        assertEquals(modifiedUser.getNickname(), nickname);
        assertEquals(modifiedUser.getBirthYear(), birthYear);
        assertEquals(modifiedUser.getSetAge(), setAge);
        assertEquals(modifiedUser.getGender(), gender);

        // exist info
        assertEquals(user.getUserIdx(), modifiedUser.getUserIdx());
        assertEquals(user.getDeviceToken(), modifiedUser.getDeviceToken());
        assertEquals(user.getIdentification(), modifiedUser.getIdentification());
        assertEquals(user.getStatus(), modifiedUser.getStatus());
        assertEquals(user.getEmail(), modifiedUser.getEmail());
        assertEquals(user.getType(), modifiedUser.getType());
    }

    @Test
    public void usersModifyTimeSaveTest() {
        Users user = usersRepository.save(getDummyUsers());
        char setAge = 'T';

        LocalDateTime now = LocalDateTime.now();
        user.update(user.getNickname(), user.getBirthYear(), setAge, user.getGender());

        usersRepository.save(user);

        Users modifiedUser = usersRepository.findAll().get(0);

        assertEquals(user.getCreatedAt(), modifiedUser.getCreatedAt());
        assertTrue(modifiedUser.getUpdatedAt().isAfter(now));
    }

    private static Users getDummyUsers() {
        return Users.builder()
                .nickname("nickname")
                .type("KAKAO")
                .status("ACTIVE")
                .identification("identification")
                .setAge('F')
                .deviceToken("device_token")
                .gender('M')
                .email("email@gmail.com")
                .birthYear(77)
                .build();
    }
}
