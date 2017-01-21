package com.zerdareader;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.Getter;
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
@NoArgsConstructor
@Table(name = "feeds")
@Setter
@Getter
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String rssPath;
    private String title;
    private String link;
    private String description;
    private String language;
    private String copyright;
    private LocalDateTime pubDate;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "feed")
    @JsonManagedReference
    private List<FeedItem> entries = new ArrayList<FeedItem>();

    public void addNewEntry(FeedItem feedItem) {
        entries.add(feedItem);
    }

    public void addNewEntry(SyndEntry entry) {
        FeedItem feedItem = new FeedItem();
        feedItem.setFields(entry, this);
        addNewEntry(feedItem);
    }

    public void setFields(TempSyndFeedStorage storage) {
        setRssPath(storage.getRssPath());
        setTitle(storage.getSyndFeed().getTitle());
        setLink(storage.getSyndFeed().getLink());
        setDescription(storage.getSyndFeed().getDescription());
        setLanguage(storage.getSyndFeed().getLanguage());
        setCopyright(storage.getSyndFeed().getCopyright());
        setPubDate(LocalDateTime.ofInstant(storage.getSyndFeed().getPublishedDate().toInstant(), ZoneId.systemDefault()));
    }
}
