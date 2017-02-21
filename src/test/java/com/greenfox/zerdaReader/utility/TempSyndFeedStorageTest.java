package com.greenfox.zerdaReader.utility;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.domain.Feed;
import com.rometools.rome.io.FeedException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.MalformedURLException;

/**
 * Created by Rita on 2017-02-05.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZerdaReaderApplication.class)
@Ignore
public class TempSyndFeedStorageTest {

    TempSyndFeedStorage storage;

    @Test(expected = MalformedURLException.class)
    public void TestConstructor1() throws Exception {
        storage = new TempSyndFeedStorage("index.hu");
    }

    @Test
    public void TestConstructor2() throws Exception {
        storage = new TempSyndFeedStorage("http://lorem-rss.herokuapp.com/feed?unit=second&interval=30");
    }

    @Test(expected = FeedException.class)
    public void TestConstructor3() throws Exception {
        storage = new TempSyndFeedStorage("file:src/test/resources/rssFeedWithError.xml");


    }

    @Test
    public void TestconvertToFeedCheckClass() throws Exception {
        storage = new TempSyndFeedStorage("http://lorem-rss.herokuapp.com/feed?unit=second&interval=30");
        Assert.assertEquals(Feed.class, storage.convertToFeed().getClass());
    }

    @Test
    public void TestconvertToFeedCheckOutputContent() throws Exception {
        storage = new TempSyndFeedStorage("http://lorem-rss.herokuapp.com/feed?unit=second&interval=30");
        Assert.assertEquals("http://lorem-rss.herokuapp.com/feed?unit=second&interval=30", storage.convertToFeed().getRssPath());
        Assert.assertEquals(10, storage.convertToFeed().getEntries().size());
    }

}