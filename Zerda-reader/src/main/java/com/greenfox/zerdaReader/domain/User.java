package com.greenfox.zerdaReader.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
    private List<Feed> subscribedFeeds;

    public User(int accessToken){
        this.accessToken = accessToken;
        subscribedFeeds = new ArrayList<>();
    }
}

