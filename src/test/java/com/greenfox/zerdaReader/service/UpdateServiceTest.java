package com.greenfox.zerdaReader.service;


/*
 * Created by ${rudolfps} on 2017.02.02..
 */

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.repository.FeedItemRepository;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import com.greenfox.zerdaReader.utility.TempSyndFeedStorage;
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
    private FeedsForUsersRepository feedsForUsersRepository;



    @Test
    @Sql({"/clear-tables.sql", "/noupdate.sql"})
    public void noUpdateNeededTest() throws Exception {
        feedService.addNewFeed("file:///D:\\Zerda-reader-Olivin\\src\\test\\resources\\indexrss.xml");
       updateService.update();
        String str = "2017-02-06 11:05";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Assert.assertEquals(dateTime,feedRepository.findOneByRssPath("file:///D:\\Zerda-reader-Olivin\\src\\test\\resources\\indexrss.xml").getPubDate());
    }


    @Test
    @Sql({"/clear-tables.sql"})
    public void updateNeededTest() throws Exception {
      // feedService.addNewFeed("file:///D:\\Zerda-reader-Olivin\\src\\test\\resources\\indexrss.xml");
      feedService.addNewFeed("file:///D:\\Zerda-reader-Olivin\\src\\test\\resources\\indexrssforupdate.xml");
      updateService.update();
       String str = "2017-02-06 14:25";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Assert.assertEquals(dateTime,feedRepository.findOneByRssPath("file:///D:\\Zerda-reader-Olivin\\src\\test\\resources\\indexrssforupdate.xml").getPubDate());
        Assert.assertEquals(2,feedItemRepository.count());
    }

    @Test
    @Sql({"/clear-tables.sql","/update.sql"})
    public void feedForUsersTablepopulated() throws Exception {
        // feedService.addNewFeed("file:///D:\\Zerda-reader-Olivin\\src\\test\\resources\\indexrss.xml");
        feedService.addNewFeed("file:///D:\\Zerda-reader-Olivin\\src\\test\\resources\\indexrssforupdate.xml");
        updateService.update();
        Assert.assertEquals(2,feedsForUsersRepository.count());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void convertDateTest() throws Exception {
        TempSyndFeedStorage tempSyndFeedStorage = new TempSyndFeedStorage("file:///D:\\Zerda-reader-Olivin\\src\\test\\resources\\index.xml");
        String str = "2017-02-06 10:23";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Assert.assertEquals(dateTime,updateService.convertDate(tempSyndFeedStorage.getSyndFeed().getPublishedDate()));

    }

}


