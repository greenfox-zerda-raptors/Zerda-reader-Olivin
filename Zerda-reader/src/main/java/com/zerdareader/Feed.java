package com.zerdareader;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private List<FeedItem> entries = new ArrayList<FeedItem>();

    public void addNewEntries(FeedItem feedItem) {
        entries.add(feedItem);
    }
}
