package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Rita on 2017-02-01.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZerdaReaderApplication.class)
@Configuration
public class FeedServiceTest {

    @Autowired
    private FeedService feedService;

    @Before
    public void setup() {

    }

    @Test
    public void TestAddNewFeedWithPathAlreadyInDb() throws Exception {
        long initialNumOfFeeds = feedService.feedRepo.count();
        feedService.addNewFeed("http://lorem-rss.herokuapp.com/feed?unit=second&amp;interval=30");
        Assert.assertEquals(initialNumOfFeeds, feedService.feedRepo.count());
    }
}