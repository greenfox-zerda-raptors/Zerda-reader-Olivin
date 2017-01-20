package com.zerdareader.Model;

import javax.persistence.*;

/**
 * Created by zoloe on 2017. 01. 20..
 */
@Entity
public class TestUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int accessToken;
//    @OneToMany
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "TestUser")
    private int subscribedFeeds;
}
