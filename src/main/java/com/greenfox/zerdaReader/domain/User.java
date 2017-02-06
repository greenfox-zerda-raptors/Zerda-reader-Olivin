package com.greenfox.zerdaReader.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zoloe on 2017. 01. 23..
 */

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int accessToken;
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "subscribed_users")
    private List<Feed> subscribedFeeds;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private List<FeedsForUsers> feedsForUsers;

    public User(int accessToken) {
        this.accessToken = accessToken;
        subscribedFeeds = new ArrayList<>();
        feedsForUsers = new ArrayList<>();
    }

}

