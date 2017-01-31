package com.greenfox.zerdaReader.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.greenfox.zerdaReader.utility.TempSyndFeedStorage;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rita on 2017-01-18.
 */
@Entity
@NoArgsConstructor
@Table(name = "feeds")
@Data
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
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
    @ManyToMany(mappedBy = "subscribedFeeds")
    private List<User> subscribedUsers;

    private void addNewEntry(FeedItem feedItem) {
        entries.add(feedItem);
    }

    public FeedItem addNewEntry(SyndEntry entry) {
        FeedItem feedItem = new FeedItem();
        feedItem.setFields(entry, this);
        addNewEntry(feedItem);
        return feedItem;
    }

    public void setFields(TempSyndFeedStorage storage) {
        setRssPath(storage.getRssPath());
        setTitle(storage.getSyndFeed().getTitle());
        setLink(storage.getSyndFeed().getLink());
        setDescription(storage.getSyndFeed().getDescription());
        setLanguage(storage.getSyndFeed().getLanguage());
        setCopyright(storage.getSyndFeed().getCopyright());
        setPubDate(convertDate(storage.getSyndFeed().getPublishedDate()));
    }

    public List<FeedItem> updateEntries(SyndFeed syndFeed) {
        List<FeedItem> updatedFeedItems = new ArrayList<>();
        for (SyndEntry se : syndFeed.getEntries()) {
            if (convertDate(se.getPublishedDate()).isAfter(pubDate)) {
                updatedFeedItems.add(addNewEntry(se));
            }
        }
        return updatedFeedItems;
    }

    private LocalDateTime convertDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
