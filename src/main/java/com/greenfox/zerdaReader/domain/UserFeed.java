package com.greenfox.zerdaReader.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoloe on 2017. 01. 25..
 */
@Setter
@NoArgsConstructor
@Getter
@Log
public class UserFeed {

    private List<UserFeedItem> feed;

    public UserFeed(List<FeedItemsForUsers> rawFeed) {
        this.feed = new ArrayList<>();
        for (FeedItemsForUsers fi : rawFeed) {
            feed.add(new UserFeedItem(fi));
        }
    }
}
