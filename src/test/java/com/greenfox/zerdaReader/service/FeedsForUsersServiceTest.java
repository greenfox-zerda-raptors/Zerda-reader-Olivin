package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
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
//Todo átnevezni a teszteket, hogy tükrözzék hogy mit tesztelnek (átnevezett endpointController szerint kell)

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

    @Autowired
    FeedsForUsersRepository feedsForUsersRepository;


    final int DEFAULTOFFSET = 0;
    final int DEFAULTITEMS = 50;

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testPopulateFeedsForUsersForFirstNewSubscription() throws Exception {
        User user = userRepository.findOne(3L);
        Assert.assertEquals(0, service.getFeedsForUsersList(user, DEFAULTOFFSET, DEFAULTITEMS).getFeed().size());
        user.getSubscribedFeeds().add(feedRepository.findOne(2L));
        userRepository.save(user);
        service.populateFeedsForUsers(user);
        userRepository.save(user);
        user = userRepository.findOne(3L);
        Assert.assertEquals(1, service.getFeedsForUsersList(user, DEFAULTOFFSET, DEFAULTITEMS).getFeed().size());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testPopulateFeedsForUsersHavingSubscribedFeeds() throws Exception {
        User user = userRepository.findOne(2L);
        user.getSubscribedFeeds().add(feedRepository.findOne(4L));
        userRepository.save(user);
        service.populateFeedsForUsers(user);
        userRepository.save(user);
        user = userRepository.findOne(2L);
        Assert.assertEquals(3, service.getFeedsForUsersList(user, DEFAULTOFFSET, DEFAULTITEMS).getFeed().size());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testUpdateReadStatus() throws Exception {
        User user = userRepository.findOne(2L);
        Assert.assertFalse(feedsForUsersRepository.findOne(2L).isReadByUser());
        service.updateReadStatus(11L, true, user);
        Assert.assertTrue(feedsForUsersRepository.findOne(2L).isReadByUser());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testUpdateReadStatusWhenNothingChanges() throws Exception {
        User user = userRepository.findOne(2L);
        Assert.assertFalse(feedsForUsersRepository.findOne(2L).isReadByUser());
        service.updateReadStatus(11L, false, user);
        Assert.assertFalse(feedsForUsersRepository.findOne(2L).isReadByUser());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testUpdateMarkAsFavorite() throws Exception {
        User user = userRepository.findOne(2L);
        Assert.assertFalse(feedsForUsersRepository.findOne(2L).isStarred());
        service.markAsFavorite(11L, user);
        Assert.assertTrue(feedsForUsersRepository.findOne(2L).isStarred());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testMarkAsFavoriteWhenItHasAlreadyBeenMarked() throws Exception {
        User user = userRepository.findOne(2L);
        Assert.assertTrue(feedsForUsersRepository.findOne(3L).isStarred());
        service.markAsFavorite(12L, user);
        Assert.assertTrue(feedsForUsersRepository.findOne(3L).isStarred());
    }
}