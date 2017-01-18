package com.zerdareader;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rita on 2017-01-18.
 */
@Entity
@NoArgsConstructor
@Table(name = "feeds")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String link;
    private String description;
    private String language;
    private String copyright;
    private String pubDate;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "feed")
    private List<FeedItem> entries = new ArrayList<FeedItem>();
}
