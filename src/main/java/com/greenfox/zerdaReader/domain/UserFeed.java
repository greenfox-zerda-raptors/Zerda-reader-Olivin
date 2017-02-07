package com.greenfox.zerdaReader.domain;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zoloe on 2017. 01. 25..
 */
@Data
public class UserFeed {
    private ArrayList<UserFeedItem> feed;


    public UserFeed getUserFeed(User user) {
        ArrayList<UserFeedItem> allUserFeedItemsToCustomJsons = new ArrayList<>();
        for (FeedsForUsers fi : user.getFeedsForUsers()) {
            allUserFeedItemsToCustomJsons.add(new UserFeedItem(fi));
        }
        UserFeed userFeed = new UserFeed();
        userFeed.setFeed(allUserFeedItemsToCustomJsons);
        return userFeed;
    }

    public UserFeed getFilteredUserFeed(User user, Integer feed_id) {
        ArrayList<UserFeedItem> userFeedItems = new ArrayList<>();
        for (FeedsForUsers fa : user.getFeedsForUsers()) {
            if (feed_id == fa.getFeedItem().getFeed().getId()) {
                userFeedItems.add(new UserFeedItem(fa));
            }
        }
        UserFeed userFeed = new UserFeed();
        userFeed.setFeed(userFeedItems);
        return userFeed;
    }

}
