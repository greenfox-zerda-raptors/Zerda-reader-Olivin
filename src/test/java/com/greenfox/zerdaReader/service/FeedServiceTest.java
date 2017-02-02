package com.greenfox.zerdaReader.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.greenfox.zerdaReader.ZerdaReaderApplication;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Rita on 2017-02-01.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZerdaReaderApplication.class)
@Configuration
public class FeedServiceTest {

    @Autowired
    private FeedService feedService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        handleSetUpOperation();
    }

    private static void handleSetUpOperation() throws Exception {
        final IDatabaseConnection conn = getConnection();
//        final IDataSet data = getDataSet();
//        try {
//            DatabaseOperation.CLEAN_INSERT.execute(conn, data);
//        } finally {
//            conn.close();
//        }
    }

//    private static IDataSet getDataSet() throws IOException,
//            DataSetException {
//        return new FlatXmlDataSetBuilder().build(new File("D:\\Green Fox Academy\\greenfox\\Zerda-reader-Olivin\\src\\test\\resources\\dataset.xml"));
//    }

    private static IDatabaseConnection getConnection() throws ClassNotFoundException, SQLException, DatabaseUnitException {
        Class.forName("org.hsqldb.jdbcDriver");
        return new DatabaseConnection(DriverManager.getConnection("jdbc:hsqldb:sample", "sa", ""));
    }

    @Test
    @DatabaseSetup("D:\\Green Fox Academy\\greenfox\\Zerda-reader-Olivin\\src\\test\\resources\\dataset.xml")
    public void TestAddNewFeedWithPathAlreadyInDb() throws Exception {
        long initialNumOfFeeds = feedService.feedRepo.count();
        feedService.addNewFeed("http://lorem-rss.herokuapp.com/feed?unit=second&amp;interval=30");
        Assert.assertEquals(initialNumOfFeeds, feedService.feedRepo.count());
    }
}