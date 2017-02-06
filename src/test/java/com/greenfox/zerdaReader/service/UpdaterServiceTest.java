package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.domain.Feed;
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
     public void TestUpdateNeededFalse() throws Exception {

        Feed feed = feedRepository.findOne(2L);
        TempSyndFeedStorage tempSyndFeedStorage = new TempSyndFeedStorage("file:///Users/zsofiaprincz/Greenfox/Zerda-reader-Olivin/src/test/resources/index.xml");
        Assert.assertTrue(updateService.isUpdateNeeded(feed,tempSyndFeedStorage.getSyndFeed()));

    }

    public void TestUpdateNeededTrue() throws Exception {

    }

}




