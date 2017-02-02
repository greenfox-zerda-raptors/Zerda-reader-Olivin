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
 * Created by Rita on 2017-02-02.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZerdaReaderApplication.class)
@DataJpaTest
public class FeedsForUsersServiceTest {

    @Autowired
    FeedsForUsersService service;

    @Autowired
    UserRepository userRepository;

    @Test
    public void TestGetFeedItemsForUserShouldReturnNull() throws Exception {
        User user = new User(123);
        Assert.assertTrue(service.getFeedItemsForUser(user).size() == 0);
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void populateFeedsForUsers() throws Exception {
        User user = userRepository.findOne(2L);
        Assert.assertEquals(2, user.getSubscribedFeeds());
    }

}