package com.greenfox.zerdaReader.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Rita on 2017-02-01.
 */
public class FeedItemTest {

    @Test
    public void TestAddNewFeedsForUsersForIfBranch() throws Exception {
        FeedItem item = new FeedItem();
        FeedsForUsers userItem = new FeedsForUsers();
        item.addNewFeedsForUsers(userItem);
        Assert.assertEquals(1, item.getFeedsForUsers().size());
    }

    @Test
    public void TestAddNewFeedsForUsersWithoutIfBranch() throws Exception {
        FeedItem item = new FeedItem();
        item.setFeedsForUsers(new ArrayList<>());
        FeedsForUsers userItem = new FeedsForUsers();
        item.addNewFeedsForUsers(userItem);
        Assert.assertEquals(1, item.getFeedsForUsers().size());
    }
}