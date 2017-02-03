package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.FeedRepository;
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

    @Autowired
    FeedRepository feedRepository;

    @Test
    public void TestGetFeedItemsForUserShouldReturnEmptyListForNewUser() throws Exception {
        User user = new User(123);
        Assert.assertTrue(service.getFeedItemsForUser(user).size() == 0);
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestGetFeedItemsForUserShouldReturnTwo() throws Exception {
        User user = userRepository.findOne(2L);
        Assert.assertEquals(2, service.getFeedItemsForUser(user).size());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestGetFeedItemsForUserShouldReturnEmptyListForUserWithOutSubscription() throws Exception {
        User user = userRepository.findOne(3L);
        Assert.assertEquals(0, service.getFeedItemsForUser(user).size());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestPopulateFeedsForUsersForFirstNewSubscription() throws Exception {
        User user = userRepository.findOne(3L);
        Assert.assertEquals(0, service.getFeedItemsForUser(user).size());
        user.getSubscribedFeeds().add(feedRepository.findOne(2L));
        userRepository.save(user);
        service.populateFeedsForUsers(user);
        userRepository.save(user);
        user = userRepository.findOne(3L);
        Assert.assertEquals(1, service.getFeedItemsForUser(user).size());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestPopulateFeedsForUsersHavingSubscribedFeeds() throws Exception {
        User user = userRepository.findOne(2L);
        user.getSubscribedFeeds().add(feedRepository.findOne(4L));
        userRepository.save(user);
        service.populateFeedsForUsers(user);
        userRepository.save(user);
        user = userRepository.findOne(2L);
        Assert.assertEquals(3, service.getFeedItemsForUser(user).size());
    }

}