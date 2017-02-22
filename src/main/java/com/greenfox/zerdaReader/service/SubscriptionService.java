package com.greenfox.zerdaReader.service;
import com.greenfox.zerdaReader.domain.Feed;
import com.greenfox.zerdaReader.domain.User;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by zoloe on 2017. 02. 15..
 */
@Component
public class SubscriptionService {

    private UserService userService;
    private FeedService feedService;
    private UpdateService updateService;
    private FeedItemsForUsersService feedItemsForUsersService;

    @Autowired
    public SubscriptionService(UserService userService, FeedService feedService, UpdateService updateService, FeedItemsForUsersService feedItemsForUsersService) {
        this.userService = userService;
        this.feedService = feedService;
        this.updateService = updateService;
        this.feedItemsForUsersService = feedItemsForUsersService;
    }

    public String trySubscribingToFeedAndReturn(String url, User user){
        try {
            feedService.addNewFeed(url);
            subscribeLoggedInUserToFeed(url, user);
            feedItemsForUsersService.populateFeedItemsForOneFeedAndUser(user,feedService.getFeedByUrl(url));
            updateService.updateFeedForUserByUrl(url, user);
            return  "{\"result\": \"subscribed\",\"id\": " + getFeedIdByUrl(url) + "}";
        } catch (Exception e) {
            return "{\"result\": \"fail\",\"message\": \"The URL provided is not valid.\"}";
        }
    }

    private void subscribeLoggedInUserToFeed(String url, User user) throws IOException, FeedException{
        Feed feed = feedService.getFeedByUrl(url);
        user.getSubscribedFeeds().add(feed);
        userService.saveUser(user);
    }

    private long getFeedIdByUrl(String url){
        return feedService.getFeedByUrl(url).getId();
    }

}
