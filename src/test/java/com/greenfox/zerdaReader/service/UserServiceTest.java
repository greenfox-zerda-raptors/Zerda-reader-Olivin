package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
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
 * Created by Rita on 2017-02-07.
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

}