package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ${rudolfps} on 2017.02.06..
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZerdaReaderApplication.class)
@DataJpaTest
public class UserServiceTest {

    @Autowired
    UserService service;

    @Autowired
    UserRepository repository;

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestGenerateNewToken() {
        User user = repository.findOne(2L);
        String originalToken = user.getToken();
        service.generateNewToken(user);
        Assert.assertNotEquals(originalToken, user.getToken());
    }

    @Test
    public void TestGenerateResponseForLoginSuccessful() {
        User user = service.addNewUser("name@example.com", "1234");
        Assert.assertEquals("{\"result\": \"success\", \"token\": \"" + user.getToken() + "\", \"id\": " + user.getId() + "}", service.generateResponseForLogin("name@example.com", "1234"));
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestGenerateResponseForSignUpFails() throws Exception {
        User user = repository.findOneByEmail("name@example.com");
        String answer = service.generateResponseForSignUp(false, user);
        Assert.assertEquals("{\"result\": \"fail\", \"message\": \"email address already exists\"}", answer);
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestGenerateResponseForSignUpSuccess() throws Exception {
        User user = repository.findOneByEmail("name@example.com");
        String answer = service.generateResponseForSignUp(true, user);
        Assert.assertEquals("{\"result\": \"success\", \"token\": \"ABCD1234\", \"id\": 2}", answer);
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestSuccessfullyAddNewUser() throws Exception {
        Assert.assertEquals(2, repository.count());
        service.addNewUser("example@gmail.com", "sajhdjahd");
        Assert.assertEquals(3, repository.count());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestIsExistingEmailShouldReturnTrue() throws Exception {
        Assert.assertTrue(service.isExistingEmail("name@example.com"));
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestIsExistingEmailShouldReturnFalse() throws Exception {
        Assert.assertFalse(service.isExistingEmail("name2@example.com"));
    }

    @Test
    @Sql({"/clear-tables.sql", "/Userrepo.sql"})
    public void getUserTest() throws Exception {
        User testUser = new User("ABCD1234");
        repository.save(testUser);
        Assert.assertEquals(2, repository.findOne(2L).getId());
    }
}