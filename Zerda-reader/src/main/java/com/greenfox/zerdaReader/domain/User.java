package com.greenfox.zerdaReader.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;


/**
 * Created by zoloe on 2017. 01. 23..
 */

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class  User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int accessToken;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subscribed_users")
    private List<Feed> subscribedFeeds;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private List<FeedsForUsers> feedsForUsers;
    public User(int accessToken){
        this.accessToken = accessToken;
    }
    public void populateFeedsForUsers() {
        for (Feed f :this.getSubscribedFeeds()
                ) {
            for (FeedItem fi:f.getEntries()
                    ) {
                if(!(this.getFeedsForUsers().contains(fi))){
                    this.getFeedsForUsers().add(new FeedsForUsers(this,fi));
                }
            }
        }
    }
//
//    public List<FeedItem> getFeedsItemsForUsers(){
//        feedsForUsers.
//
//        return
//    }
//
}
