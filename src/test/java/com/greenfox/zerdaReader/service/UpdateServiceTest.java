package com.greenfox.zerdaReader.service;


/*
 * Created by ${rudolfps} on 2017.02.02..
 */

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.repository.FeedItemRepository;
import com.greenfox.zerdaReader.repository.FeedRepository;
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



    @Test
    @Sql({"/clear-tables.sql", "/noupdateneeded.sql"})
    public void noUpdateNeededTest() throws Exception {
        feedService.addNewFeed("file:///D:\\Zerda-reader-Olivin\\src\\test\\resources\\indexrss.xml");
        TempSyndFeedStorage storage = new TempSyndFeedStorage("file:///D:\\Zerda-reader-Olivin\\src\\test\\resources\\indexrss.xml");
        updateService.update();
        String str = "2017-02-06 11:05";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Assert.assertEquals(dateTime,feedRepository.findOneByRssPath("file:///D:\\Zerda-reader-Olivin\\src\\test\\resources\\indexrss.xml").getPubDate());
    }

  /*  @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void updateNeededTest() throws Exception {

    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void feedPubdateTest() throws Exception {

    }*/
}


