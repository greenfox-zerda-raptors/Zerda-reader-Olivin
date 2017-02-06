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
    UserRepository userRepository;
    UserService userService;


    @Test
    @Sql({"/clear-tables.sql","/Userrepo.sql"})
    public void getUserTest() throws Exception {
        User testUser = new User(1234);
        userRepository.save(testUser);
        Assert.assertEquals(2,userRepository.findOne(2L).getId());


    }

}