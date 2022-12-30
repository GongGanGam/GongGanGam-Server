package site.gonggangam.gonggangam_server.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
<<<<<<< Updated upstream
import site.gonggangam.gonggangam_server.dto.users.PatchUsersRequestDto;
=======
import site.gonggangam.gonggangam_server.domain.ActiveStatus;
import site.gonggangam.gonggangam_server.domain.users.types.AuthType;
import site.gonggangam.gonggangam_server.domain.users.types.Gender;
import site.gonggangam.gonggangam_server.domain.users.types.ShareType;
import site.gonggangam.gonggangam_server.dto.users.UsersRequestDto;
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
        usersService.update(user.getUserIdx(), new PatchUsersRequestDto(
                nickname, birthYear, setAge, gender
=======
        usersService.update(user.getUserIdx(), new UsersRequestDto(
                nickname, birthYear, shareType, gender
>>>>>>> Stashed changes
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
        assertEquals(user.getActiveStatus(), modifiedUser.getActiveStatus());
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
<<<<<<< Updated upstream
                .type("KAKAO")
                .status("ACTIVE")
=======
                .authType(AuthType.KAKAO)
                .status(ActiveStatus.INACTIVE)
>>>>>>> Stashed changes
                .identification("identification")
                .setAge('F')
                .deviceToken("device_token")
                .gender('M')
                .email("email@gmail.com")
                .birthYear(77)
                .build();
    }
}
