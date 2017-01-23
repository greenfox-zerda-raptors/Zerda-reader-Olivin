package com.greenfox.zerdaReader.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by zoloe on 2017. 01. 23..
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadStatusAndStarred {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;
    private boolean starred;
    private boolean readByUser;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "feedItem_id")
    private FeedItem feedItem;


    public ReadStatusAndStarred(boolean starred, boolean readByUser, User user, FeedItem feedItem) {
        this.starred = starred;
        this.readByUser = readByUser;
        this.user = user;
        this.feedItem = feedItem;
    }
}

