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
 * Created by ${rudolfps} on 2017.02.06..
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZerdaReaderApplication.class)
@DataJpaTest
public class FeedItemServiceTest {

    @Autowired
    FeedItemRepository feedItemRepository;

    FeedItemService itemService;

    @Test
    @Sql({"/clear-tables.sql", "/noupdate.sql"})
    public void getFeedItem() throws Exception {
        itemService = new FeedItemService(feedItemRepository);
        Assert.assertEquals("Félmilliárdból épül a szovjet megszállási emlékmű",itemService.getFeedItem(9L).getTitle());
        }

}