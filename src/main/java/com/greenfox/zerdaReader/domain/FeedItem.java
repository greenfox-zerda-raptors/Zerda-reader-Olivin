package com.greenfox.zerdaReader.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rita on 2017-01-18.
 */
@Entity
@Table(name = "feed_items")
@NoArgsConstructor
public class FeedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String link;
    private String author;
    private LocalDateTime pubDate;
    @ManyToOne
    @JoinColumn(name = "feed_id")
    @JsonBackReference
    private Feed feed;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "feedItem")
    private List<FeedsForUsers> feedsForUsers;

    public void setFields(SyndEntry entry, Feed feed) {
        setTitle(entry.getTitle());
        setDescription(entry.getDescription().getValue());
        setLink(entry.getLink());
        setAuthor(entry.getAuthor());
        setPubDate(LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(), ZoneId.systemDefault()));
        setFeed(feed);
    }

    public void addNewFeedsForUsers(FeedsForUsers feedsForUser) {
        if (feedsForUsers == null) {
            feedsForUsers = new ArrayList<>();
        }
        feedsForUsers.add(feedsForUser);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDateTime pubDate) {
        this.pubDate = pubDate;
    }

    @JsonIgnore
    @JsonBackReference
    public Feed getFeed() {
        return feed;
    }

    @JsonBackReference
    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    @JsonIgnore
    public List<FeedsForUsers> getFeedsForUsers() {
        return feedsForUsers;
    }

    public void setFeedsForUsers(List<FeedsForUsers> feedsForUsers) {
        this.feedsForUsers = feedsForUsers;
    }


}
