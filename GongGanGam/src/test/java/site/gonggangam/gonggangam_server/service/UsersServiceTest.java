package site.gonggangam.gonggangam_server.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.gonggangam.gonggangam_server.repository.UsersRepository;

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

    }

    @Test
    public void usersModifyTimeSaveTest() {

    }

}
