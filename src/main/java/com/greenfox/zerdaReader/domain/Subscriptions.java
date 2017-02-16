package com.greenfox.zerdaReader.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rita on 2017-02-15.
 */
@Getter
public class Subscriptions {
    List<SubscribedFeed> subscribedFeedList;

    public Subscriptions(List<Feed> feeds) {
        subscribedFeedList = new ArrayList<>();
        for (Feed feed : feeds) {
            subscribedFeedList.add(new SubscribedFeed(feed.getTitle(), feed.getId()));
        }
    }
}
