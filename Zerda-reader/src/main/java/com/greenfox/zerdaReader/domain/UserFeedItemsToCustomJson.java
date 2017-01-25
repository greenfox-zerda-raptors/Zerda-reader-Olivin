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


    public UserFeedItemsToCustomJson(FeedItem feedItem,
                                     FeedsForUsers feedsForUsers) {
        this.id = feedItem.getId();
        this.title = feedItem.getTitle();
        this.description = feedItem.getDescription();
//        this.created = feedItem.getPubDate(); //atirni java utildatere moindekhol a pubdatet
        this.feed_name = feedItem.getFeed().getTitle();
        this.feed_id = feedItem.getFeed().getId();
        this.favorite = feedsForUsers.isStarred();
        this.opened = feedsForUsers.isReadByUser();
        this.url = feedItem.getLink();
    }
}
