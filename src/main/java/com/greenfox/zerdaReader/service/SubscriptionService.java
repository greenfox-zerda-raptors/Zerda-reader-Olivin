package com.greenfox.zerdaReader.service;
import com.greenfox.zerdaReader.domain.Feed;
import com.greenfox.zerdaReader.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zoloe on 2017. 02. 15..
 */
@Component
public class SubscriptionService {

    UserService userService;
    FeedService feedService;

    @Autowired
    public SubscriptionService(UserService userService, FeedService feedService) {
        this.userService = userService;
        this.feedService = feedService;
    }

    public String trySubscribingToFeedAndReturn(String url){
//        probaljuk meg hozzaadni a feedet a DB-hez (ha mar nalunk van, nem tortenik semmi
        try {
              feedService.addNewFeed(url);
        } catch (Exception e) {
//            ha nem sikerult hozzaadni (exceptiont dobott), akkor szar az url, terjunk vissza hibauzenettel
            return "{\"result\": \"fail\",\"message\": \"The URL provided is not valid.\"}";
        }
//            iratkoztassuk fol ra a usert
            subscribeLoggedInUserToAlreadyParsedFeed(url);
//              terjunk vissza az id-val
        return  "{\"result\": \"subscribed\",\"id\": " + getFeedIdByUrl(url) + "}";
    }

    private void subscribeLoggedInUserToAlreadyParsedFeed(String url){
        User user = userService.getLoggedInUser();
        Feed feed = feedService.getFeedByUrl(url);
        user.getSubscribedFeeds().add(feed);
        userService.saveUser(user);
    }

    private long getFeedIdByUrl(String url){
        return feedService.getFeedByUrl(url).getId();
    }

}
