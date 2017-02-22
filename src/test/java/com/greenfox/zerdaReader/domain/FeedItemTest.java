package com.greenfox.zerdaReader.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Rita on 2017-02-01.
 */
public class FeedItemTest {

    @Test
    public void testAddNewFeedsForUsersForIfBranch() throws Exception {
        FeedItem item = new FeedItem();
        FeedItemsForUsers userItem = new FeedItemsForUsers();
        item.addNewFeedItemsForUsers(userItem);
        Assert.assertEquals(1, item.getFeedItemsForUsers().size());
    }

    @Test
    public void testAddNewFeedsForUsersWithoutIfBranch() throws Exception {
        FeedItem item = new FeedItem();
        item.setFeedItemsForUsers(new ArrayList<>());
        FeedItemsForUsers userItem = new FeedItemsForUsers();
        item.addNewFeedItemsForUsers(userItem);
        Assert.assertEquals(1, item.getFeedItemsForUsers().size());
    }
}