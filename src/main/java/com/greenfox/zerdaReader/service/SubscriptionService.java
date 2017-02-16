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

    UserService userService;
    FeedService feedService;
    UpdateService updateService;

    @Autowired
    public SubscriptionService(UserService userService, FeedService feedService) {
        this.userService = userService;
        this.feedService = feedService;
    }

    public String trySubscribingToFeedAndReturn(String url){
//        probaljuk meg hozzaadni a feedet a DB-hez (ha mar nalunk van, nem adodik hozza, le van kezelve
        try {
              feedService.addNewFeed(url);
            //    frissitsuk be a frissen hozzaadott feedet, hogy a user ebbol biztosan ujat kapjon, ha lekeri
                updateService.updateFeedForUserByUrl(url, userService.getLoggedInUser());
            //TODO: triggerelni kene egy update-et
            //TODO: a feeds_for_users-be bekerulnek a feeditemek?

        } catch (Exception e) {
//            ha nem sikerult hozzaadni (exceptiont dobott), akkor szar az url, terjunk vissza hibauzenettel
            return "{\"result\": \"fail\",\"message\": \"The URL provided is not valid.\"}";
        }
//            iratkoztassuk fol ra a usert
        try {
            subscribeLoggedInUserToAlreadyParsedFeed(url);
//              terjunk vissza az id-val
            return  "{\"result\": \"subscribed\",\"id\": " + getFeedIdByUrl(url) + "}";
        }catch (Exception e){};

        return "{\"result\": \"fail\",\"message\": \"The URL provided is not valid.\"}";
    }

    private void subscribeLoggedInUserToAlreadyParsedFeed(String url) throws IOException, FeedException{
        User user = userService.getLoggedInUser();
        Feed feed = feedService.getFeedByUrl(url);
        user.getSubscribedFeeds().add(feed);
//        feed.getSubscribedUsers().add(user);
//        feedService.saveFeed(feed);
        userService.saveUser(user);
//Todo: letesztelni, hody a feeds for users lementodik-e.
    }


    private long getFeedIdByUrl(String url){
        return feedService.getFeedByUrl(url).getId();
    }

}
