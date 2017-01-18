package com.zerdareader;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Rita on 2017-01-18.
 */
@Entity
@Table(name = "feed_items")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private String link;
    private String author;
    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;

    public FeedItem(String title, String description, String link, String author) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.author = author;
    }
}
