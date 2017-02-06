package com.greenfox.zerdaReader.domain;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.repository.FeedItemRepository;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.service.UpdateService;
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
import java.util.Date;

/**
 * Created by zsofiaprincz on 02/02/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZerdaReaderApplication.class)
@DataJpaTest
public class UpdaterServiceTest {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FeedItemRepository feedItemRepository;

    @Autowired
    private UpdateService updateService;

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
     public void TestUpdateNeeded() throws Exception {

        Feed feed = feedRepository.findOne(2L);
        LocalDateTime feedDateinDb = feed.getPubDate();
        TempSyndFeedStorage tempSyndFeedStorage = new TempSyndFeedStorage("file:///Users/zsofiaprincz/Greenfox/Zerda-reader-Olivin/src/test/resources/index.xml");
        Date dateOfXml = tempSyndFeedStorage.getSyndFeed().getPublishedDate();
        Assert.assertTrue(updateService.isUpdateNeeded(feed,tempSyndFeedStorage.getSyndFeed()));

    }

}




