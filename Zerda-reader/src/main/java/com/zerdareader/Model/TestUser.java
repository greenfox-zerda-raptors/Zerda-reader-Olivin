package com.zerdareader.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zerdareader.Feed;
import com.zerdareader.FeedItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zoloe on 2017. 01. 20..
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class TestUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int accessToken;
////    @OneToMany
//    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "TestUser")
//    private  SubscribedFeeds subscribedFeeds;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "feeds_for_users")
    private List<Feed> subscribedFeeds;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "feedItems_for_users")
    private List<FeedItem> subscribedFeedItems;

    public TestUser(int accessToken){
        this.accessToken = accessToken;
        subscribedFeeds = new ArrayList<>();
        }
    }



