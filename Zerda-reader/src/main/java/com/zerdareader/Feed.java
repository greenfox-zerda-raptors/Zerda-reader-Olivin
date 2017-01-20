package com.zerdareader;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String title;
    private String link;
    private String description;
    private String language;
    private String copyright;
    private String pubDate;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "feed")
    @JsonManagedReference
    private List<FeedItem> entries = new ArrayList<FeedItem>();
}
