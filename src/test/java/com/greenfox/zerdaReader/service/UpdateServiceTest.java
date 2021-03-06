package com.greenfox.zerdaReader.service;


/*
 * Created by ${rudolfps} on 2017.02.02..
 */

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.domain.Feed;
import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.repository.FeedItemRepository;
import com.greenfox.zerdaReader.repository.FeedItemsForUsersRepository;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.utility.TempSyndFeedStorage;
import com.rometools.rome.feed.synd.SyndEntry;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZerdaReaderApplication.class)
@DataJpaTest
public class UpdateServiceTest {


    @Autowired
    private FeedItemRepository feedItemRepository;

    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private FeedService feedService;
    @Autowired
    private UpdateService updateService;

    @Autowired
    private FeedItemsForUsersRepository feedItemsForUsersRepository;


    @Test
    @Sql({"/clear-tables.sql", "/noupdate.sql"})
    public void noUpdateNeededTest() throws Exception {
        feedService.addNewFeed("file:src/test/resources/indexrss.xml");
       updateService.update();
        String str = "2017-02-06 11:05";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Assert.assertEquals(dateTime, feedRepository.findOneByRssPath("file:src/test/resources/indexrss.xml").getPubDate());
    }


    @Test
    @Sql({"/clear-tables.sql"})
    public void updateNeededTest() throws Exception {
        feedService.addNewFeed("file:src/test/resources/indexrssforupdate.xml");
        updateService.update();
        String str = "2017-02-06 14:25";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Assert.assertEquals(dateTime, feedRepository.findOneByRssPath("file:src/test/resources/indexrssforupdate.xml").getPubDate());
        Assert.assertEquals(2, feedItemRepository.count());
    }

    @Test
    @Sql({"/clear-tables.sql", "/update.sql"})
    public void feedForUsersTablepopulated() throws Exception {
        feedService.addNewFeed("file:src/test/resources/indexrssforupdate.xml");
        updateService.update();
        Assert.assertEquals(2, feedItemsForUsersRepository.count());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void convertDateTest() throws Exception {
        TempSyndFeedStorage tempSyndFeedStorage = new TempSyndFeedStorage("file:src/test/resources/index.xml");
        String str = "2017-02-06 10:23";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Assert.assertEquals(dateTime, updateService.convertDate(tempSyndFeedStorage.getSyndFeed().getPublishedDate()));

    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestUpdateNeededTrue() throws Exception {
        Feed feed = feedRepository.findOne(2L);
        TempSyndFeedStorage tempSyndFeedStorage = new TempSyndFeedStorage("file:src/test/resources/index.xml");
        Assert.assertTrue(updateService.isUpdateNeeded(feed, tempSyndFeedStorage.getSyndFeed()));
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestNumberOfUpdatedFeedItems() throws Exception {
        Feed feed = feedRepository.findOne(2L);
        TempSyndFeedStorage tempSyndFeedStorage = new TempSyndFeedStorage("file:src/test/resources/index.xml");
        updateService.isUpdateNeeded(feed, tempSyndFeedStorage.getSyndFeed());
        for (SyndEntry se : tempSyndFeedStorage.getSyndFeed().getEntries()) {
            updateService.convertDate(se.getPublishedDate()).isAfter(feed.getPubDate());
            FeedItem feedItem = new FeedItem();
            feedItem.setFields(se, feed);
            feedItemRepository.save(feedItem);
            Lists.newArrayList(feedItemRepository.findAll()).size();
        }
        Assert.assertEquals(4, Lists.newArrayList(feedItemRepository.findAll()).size());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables3.sql"})
    public void isAfterShouldReturnFalse() throws Exception {
        Feed feed = feedRepository.findOne(4L);
        TempSyndFeedStorage storage = new TempSyndFeedStorage("file:src/test/resources/indexrssforupdate.xml");
        updateService.update();
        Assert.assertFalse(updateService.convertDate(storage.getSyndFeed().getPublishedDate()).isAfter(feed.getPubDate()));
    }

    //    TODO: updateFeedForUserByUrl test with logged in user
    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestNumberOfUpdatedFeedItemsForUserByUrl() throws Exception {
        Feed feed = feedRepository.findOne(2L);
        TempSyndFeedStorage tempSyndFeedStorage = new TempSyndFeedStorage("file:src/test/resources/index.xml");
        updateService.isUpdateNeeded(feed, tempSyndFeedStorage.getSyndFeed());
        for (SyndEntry se : tempSyndFeedStorage.getSyndFeed().getEntries()) {
            updateService.convertDate(se.getPublishedDate()).isAfter(feed.getPubDate());
            FeedItem feedItem = new FeedItem();
            feedItem.setFields(se, feed);
            feedItemRepository.save(feedItem);
            Lists.newArrayList(feedItemRepository.findAll()).size();
        }
        Assert.assertEquals(4, Lists.newArrayList(feedItemRepository.findAll()).size());
    }






//    @Before
//    public void setUp(){
//        OutOfMemoryError outOfMemoryError= new OutOfMemoryError();
//    }
//
//    public void outOfMemoryErrorTest () throws Exception{
//
//        Assert.assertEquals()
//    }

}