package com.greenfox.zerdaReader.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by zoloe on 2017. 01. 23..
 */

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int accessToken;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "feeds_for_users")
    private Set<Feed> subscribedFeeds;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<ReadStatusAndStarred> readStatusAndStarred;

    public User(int accessToken){
        this.accessToken = accessToken;
        subscribedFeeds = new HashSet<>();
        readStatusAndStarred = new HashSet<>();
    }
}

