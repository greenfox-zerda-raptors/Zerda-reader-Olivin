package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.repository.FeedItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Rita on 2017-02-01.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZerdaReaderApplication.class)
@DataJpaTest
public class FeedServiceTest {

    @Autowired
    private FeedService feedService;

    @Autowired
    private FeedItemRepository feedItemRepository;

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testAddNewFeedWithPathNotYetInDb() throws Exception {
        long initialNumOfFeeds = feedService.feedRepo.count();
        long initialNumOfFeedItems = feedItemRepository.count();
        feedService.addNewFeed("http://lorem-rss.herokuapp.com/feed?unit=second&amp;interval=60");
        Assert.assertEquals(initialNumOfFeeds + 1, feedService.feedRepo.count());
        Assert.assertEquals(initialNumOfFeedItems + 10, feedItemRepository.count());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testAddNewFeedWithPathAlreadyInDb() throws Exception {
        long initialNumOfFeeds = feedService.feedRepo.count();
        long initialNumOfFeedItems = feedItemRepository.count();
        Assert.assertEquals(3, initialNumOfFeeds);
        feedService.addNewFeed("http://lorem-rss.herokuapp.com/feed?unit=second&interval=30");
        Assert.assertEquals(initialNumOfFeeds, feedService.feedRepo.count());
        Assert.assertEquals(initialNumOfFeedItems, feedItemRepository.count());
    }
}