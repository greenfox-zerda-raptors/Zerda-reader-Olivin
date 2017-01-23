package com.greenfox.zerdaReader.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Rita on 2017-01-18.
 */
@Entity
@Table(name = "feed_items")
@Data
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

    public void setFields(SyndEntry entry, Feed feed) {
        setTitle(entry.getTitle());
        setDescription(entry.getDescription().getValue());
        setLink(entry.getLink());
        setAuthor(entry.getAuthor());
        setPubDate(LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(), ZoneId.systemDefault()));
        setFeed(feed);
    }
}
