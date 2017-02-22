package com.greenfox.zerdaReader.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rita on 2017-02-15.
 */
@Getter
public class Subscriptions {
    List<SubscribedFeed> subscriptions;

    public Subscriptions(List<Feed> feeds) {
        subscriptions = new ArrayList<>();
        for (Feed feed : feeds) {
            subscriptions.add(new SubscribedFeed(feed.getTitle(), feed.getId()));
        }
    }
}
