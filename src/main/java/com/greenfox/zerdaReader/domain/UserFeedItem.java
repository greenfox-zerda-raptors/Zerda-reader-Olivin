package com.greenfox.zerdaReader.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.util.Date;

/**
 * Created by zoloe on 2017. 01. 24..
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserFeedItem {

    private long id;
    private String title;
    private String description;
    private Date created;
    private String feed_name;
    private long feed_id;
    private boolean favorite;
    private boolean opened;
    private String url;

    public UserFeedItem(FeedItemsForUsers feedItemsForUsers) {
        this.id = feedItemsForUsers.getFeedItem().getId();
        this.title = feedItemsForUsers.getFeedItem().getTitle();
        this.description = feedItemsForUsers.getFeedItem().getDescription();
        this.created = new Date(feedItemsForUsers.getFeedItem().getPubDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        this.feed_name = feedItemsForUsers.getFeedItem().getFeed().getTitle();
        this.feed_id = feedItemsForUsers.getFeedItem().getFeed().getId();
        this.favorite = feedItemsForUsers.isStarred();
        this.opened = feedItemsForUsers.isReadByUser();
        this.url = feedItemsForUsers.getFeedItem().getLink();
    }
}
