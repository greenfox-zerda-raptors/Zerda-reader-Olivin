package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntryImpl;

import javax.persistence.*;

/**
 * Created by Rita on 2017-01-18.
 */
@Entity
@Table(name = "feed_items")
public class FeedItem extends SyndEntryImpl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
