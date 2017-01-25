package com.greenfox.zerdaReader.domain;

import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.FeedsForUsers;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import lombok.Data;

import java.util.Date;

/**
 * Created by zoloe on 2017. 01. 24..
 */
@Data
public class UserFeedItemsToCustomJson {
    private long id;
    private String title;
    private String description;
    private Date created;
    private String feed_name;
    private long feed_id;
    private boolean favorite;
    private boolean opened;
    private String url;


    public UserFeedItemsToCustomJson(FeedsForUsers feedsForUsers) {
        this.id = feedsForUsers.getFeedItem().getId();
        this.title = feedsForUsers.getFeedItem().getTitle();
        this.description = feedsForUsers.getFeedItem().getDescription();
//        this.created = feedItem.getPubDate(); //atirni java utildatere moindekhol a pubdatet
        this.feed_name = feedsForUsers.getFeedItem().getFeed().getTitle();
        this.feed_id = feedsForUsers.getFeedItem().getFeed().getId();
        this.favorite = feedsForUsers.isStarred();
        this.opened = feedsForUsers.isReadByUser();
        this.url = feedsForUsers.getFeedItem().getLink();
    }
}
