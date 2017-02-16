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

    public UserFeedItem(FeedsForUsers feedsForUsers) {
        this.id = feedsForUsers.getFeedItem().getId();
        this.title = feedsForUsers.getFeedItem().getTitle();
        this.description = feedsForUsers.getFeedItem().getDescription();
        this.created = new Date(feedsForUsers.getFeedItem().getPubDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        this.feed_name = feedsForUsers.getFeedItem().getFeed().getTitle();
        this.feed_id = feedsForUsers.getFeedItem().getFeed().getId();
        this.favorite = feedsForUsers.isStarred();
        this.opened = feedsForUsers.isReadByUser();
        this.url = feedsForUsers.getFeedItem().getLink();
    }
}
