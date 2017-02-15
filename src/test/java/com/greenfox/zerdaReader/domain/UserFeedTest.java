package com.greenfox.zerdaReader.domain;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.repository.UserRepository;
import com.greenfox.zerdaReader.service.FeedsForUsersService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zoloe on 2017. 02. 06..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZerdaReaderApplication.class)
@DataJpaTest
public class UserFeedTest {

    @Autowired
    UserRepository userRepository;
    final int DEFAULTOFFSET = 0;
    final int DEFAULTITEMS = 50;
    @Autowired
    FeedsForUsersService service;

    @Test
    public void TestGetUserFeedShouldReturnEmptyListForNewUser() throws Exception {
        User user = new User("123");
        Assert.assertTrue(service.getFeedsForusersList(user, DEFAULTOFFSET, 0).getFeed().size() == 0);
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestGetUserFeedShouldReturnTwo() throws Exception {
        User user = userRepository.findOne(2L);
        Assert.assertEquals(2, service.getFeedsForusersList(user, DEFAULTOFFSET, DEFAULTITEMS).getFeed().size());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestGetUserFeedForUserShouldReturnEmptyListForUserWithOutSubscription() throws Exception {
        User user = userRepository.findOne(3L);
        Assert.assertEquals(0,service.getFeedsForusersList(user, DEFAULTOFFSET, DEFAULTITEMS).getFeed().size());
    }


}
