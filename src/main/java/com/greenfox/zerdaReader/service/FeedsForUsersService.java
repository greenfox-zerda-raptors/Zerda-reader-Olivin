package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.*;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public void addFeedsForUsers(User user, FeedItem feedItem) {
        user.getFeedsForUsers().add(new FeedsForUsers(user, feedItem));
        feedsForUsersRepository.save(user.getFeedsForUsers());
    }

    public void populateFeedsForUsersforOneFeed(List<User> users, Feed feed) {
        for (User user : users) {
            for (FeedItem fi : feed.getEntries()) {
                if (feedsForUsersRepository.findByUserAndFeedItem(user, fi) == null) {
                    user.getFeedsForUsers().add(new FeedsForUsers(user, fi));
                }
                feedsForUsersRepository.save(user.getFeedsForUsers());
            }
        }
    }

    public void populateFeedsForUsersforFeedItems(List<User> users, List<FeedItem> feedItems) {
        for (User user : users) {
            for (FeedItem feedItem : feedItems) {
                if (feedsForUsersRepository.findByUserAndFeedItem(user, feedItem) == null) {
                    user.getFeedsForUsers().add(new FeedsForUsers(user, feedItem));
                }
            }
            feedsForUsersRepository.save(user.getFeedsForUsers());
        }
    }

    public void nothing(){

        int a=3;
        int b=2;
        
    }
}
