package com.zerdareader;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zerdareader.Model.TestUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
//    @JsonIgnore
    private Feed feed;

    @ManyToMany(mappedBy = "subscribedFeedItems")
    private List<TestUser> subscribedUsers;


    public FeedItem(String title, String description, String link, String author) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.author = author;
    }
}
