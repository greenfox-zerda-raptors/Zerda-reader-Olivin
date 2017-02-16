package com.greenfox.zerdaReader.service;
import com.greenfox.zerdaReader.domain.Feed;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.UserRepository;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * Created by zoloe on 2017. 02. 15..
 */
@Component
public class SubscriptionService {

    UserRepository userRepository;
    UserService userService;
    FeedService feedService;

    @Autowired
    public SubscriptionService(UserRepository userRepository, UserService userService, FeedService feedService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.feedService = feedService;
    }

    public String trySubscribingToFeedAndReturn(String url){
//            ha mar benne van a feed a DB-ben
        if (isUrlAlreadyParsed(url)) {
//            iratkoztassuk fel ra
            subscribeToAlreadyParsedFeed(url);
//            ha meg nincs benne (azaz teljesen uj url)
        }else{
            try {
//                adjuk hozza az uj feedet a feedjeinkhez, es iratkoztassuk fol ra
                subscribeToBrandNewFeed(url);
            } catch (Exception e) {
//                ha nem sikerult, terjen vissza a hibauzenettel (    if exception is raised, for example URL is malformed or the URL is 404 or not an RSS)
                return "{\"result\":\"fail\",\"message\":\"The URL provided is not valid.\"}";
            }
        }
        return  "{\"result\":\"subscribed\",\"id\":" + getFeedIdByUrl(url) + "}";
    }

    private void subscribeToAlreadyParsedFeed(String url){
        User user = userService.getLoggedInUser();
        Feed feed = feedService.getFeedByUrl(url);
        user.getSubscribedFeeds().add(feed);
        userService.saveUser(user);
    }

    private boolean isUrlAlreadyParsed(String url){
        return (feedService.getFeedByUrl(url) != null);
    }

    private long getFeedIdByUrl(String url){
        return feedService.getFeedByUrl(url).getId();
    }


    private void subscribeToBrandNewFeed(String url) throws IOException, FeedException {
        feedService.addNewFeed(url);
        subscribeToAlreadyParsedFeed(url);
    }
}
