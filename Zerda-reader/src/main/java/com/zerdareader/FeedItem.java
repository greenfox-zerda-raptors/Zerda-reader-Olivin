package com.zerdareader;

import javax.persistence.*;

/**
 * Created by Rita on 2017-01-18.
 */
@Entity
@Table(name = "feed_items")
public class FeedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private String link;
    private String author;
    private String guid;
    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;
}
