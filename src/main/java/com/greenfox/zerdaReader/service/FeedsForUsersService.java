package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.*;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by zoloe on 2017. 01. 25..
 */
@Component
public class FeedsForUsersService {
    FeedsForUsersRepository feedsForUsersRepository;


    @Autowired
    public FeedsForUsersService(FeedsForUsersRepository feedsForUsersRepository) {
        this.feedsForUsersRepository = feedsForUsersRepository;
    }

    public ArrayList<UserFeedItemsToCustomJson> getFeedItemsForUser(User user) {
        ArrayList<UserFeedItemsToCustomJson> l = new ArrayList<>();
        for (FeedsForUsers fi : user.getFeedsForUsers()) {
            l.add(new UserFeedItemsToCustomJson(fi));
        }
        return l;
    }

    public ArrayList<UserFeedItemsToCustomJson> getFeedForUser(User user, Long feed_id) {
        ArrayList<UserFeedItemsToCustomJson> l = new ArrayList<>();
        for (FeedsForUsers fa : user.getFeedsForUsers()) {
            if (feed_id == fa.getFeedItem().getFeed().getId()) {
                l.add(new UserFeedItemsToCustomJson(fa));
            }


        }
        return l;
    }



    public void populateFeedsForUsers(User user) {
        for (Feed f : user.getSubscribedFeeds()) {
            for (FeedItem fi : f.getEntries()) {
                if (feedsForUsersRepository.findByUserAndFeedItem(user, fi) == null) {
                    user.getFeedsForUsers().add(new FeedsForUsers(user, fi));
                }
            }
        }
        feedsForUsersRepository.save(user.getFeedsForUsers());
    }
}
