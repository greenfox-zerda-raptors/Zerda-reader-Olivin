package com.zerdareader;

<<<<<<< HEAD
import lombok.*;
=======
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
>>>>>>> persistence-test

import javax.persistence.*;

/**
 * Created by Rita on 2017-01-18.
 */
@Entity
@Table(name = "feed_items")
<<<<<<< HEAD
@AllArgsConstructor
@NoArgsConstructor
@Data
=======
@Getter
@Setter
@NoArgsConstructor
>>>>>>> persistence-test
public class FeedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @Column(columnDefinition = "TEXT")
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
