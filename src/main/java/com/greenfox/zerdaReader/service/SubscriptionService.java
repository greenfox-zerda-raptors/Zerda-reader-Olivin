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

    public String trySubscribingToFeedAndReturn(String url){
//            probaljuk meg hozzaadni a feedet a DB-hez (ha mar nalunk van, nem adodik hozza, le van kezelve
        try {
            feedService.addNewFeed(url);
//            iratkoztassuk fol ra a usert
            subscribeLoggedInUserToFeed(url);
//            adjuk hozzá a (z esetleg már nálunk lévő) userfeeditemeket a userhez
            feedItemsForUsersService.populateFeedItemsForOneFeedAndUser(userService.getLoggedInUser(),feedService.getFeedByUrl(url));
//            frissitsuk be a frissen hozzaadott feedet, hogy a user ebbol biztosan ujat kapjon, amikor lekeri
            updateService.updateFeedForUserByUrl(url, userService.getLoggedInUser());
//            terjunk vissza az id-val
            return  "{\"result\": \"subscribed\",\"id\": " + getFeedIdByUrl(url) + "}";
        } catch (Exception e) {
//            ha nem sikerult hozzaadni (exceptiont dobott), akkor szar az url, terjunk vissza hibauzenettel
            return "{\"result\": \"fail\",\"message\": \"The URL provided is not valid.\"}";
        }
    }

    private void subscribeLoggedInUserToFeed(String url) throws IOException, FeedException{
        User user = userService.getLoggedInUser();
        Feed feed = feedService.getFeedByUrl(url);
        user.getSubscribedFeeds().add(feed);
        userService.saveUser(user);
    }

    private long getFeedIdByUrl(String url){
        return feedService.getFeedByUrl(url).getId();
    }

}
