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
public class EndpointService {
    FeedsForUsersRepository feedsForUsersRepository;


    @Autowired
    public EndpointService(FeedsForUsersRepository feedsForUsersRepository) {
        this.feedsForUsersRepository = feedsForUsersRepository;
    }

    public UserFeed getUserFeed(User user) {
        ArrayList<UserFeedItem> allUserFeedItemsToCustomJsons = new ArrayList<>();
        for (FeedsForUsers fi : user.getFeedsForUsers()) {
            allUserFeedItemsToCustomJsons.add(new UserFeedItem(fi));
        }
        UserFeed userFeed = new UserFeed();
        userFeed.setFeed(allUserFeedItemsToCustomJsons);
        return userFeed;
    }

    public UserFeed getFilteredUserFeed(User user, Integer feed_id) {
        ArrayList<UserFeedItem> userFeedItems = new ArrayList<>();
        for (FeedsForUsers fa : user.getFeedsForUsers()) {
            if (feed_id == fa.getFeedItem().getFeed().getId()) {
                userFeedItems.add(new UserFeedItem(fa));
            }
        }
        UserFeed userFeed = new UserFeed();
        userFeed.setFeed(userFeedItems);
        return userFeed;
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
