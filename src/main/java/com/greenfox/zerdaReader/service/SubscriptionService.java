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
    FeedService feedService;

    @Autowired
    public SubscriptionService(UserRepository userRepository, FeedService feedService) {
        this.userRepository = userRepository;
        this.feedService = feedService;
    }

    public String generateResponseForSubsciption(String url){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Feed feed = feedService.findFeedByUrl(url);

        String response;
        // to brandnew feed

        if (feed == null){
            Long newFeedId = -1L;
            try {
                newFeedId = getIdForBrandNewFeed(url);
            } catch (Exception e){
                return "{\"result\":\"fail\"}";
            }

            if (newFeedId > -1L ){
                response = "{\"result\":\"subscribed\",\"id\":" + newFeedId + "}";
            }
            // if its not a valid feed
            else {
                //    if exception is raised, for example URL is malformed or the URL is 404 or not an RSS
//                response = "{\"result\":\"fail\",\"message\":\"The URL provided is not valid.\"}";
                response = "{\"result\":\"fail\"}";
            }
        }
        // to existing feed
        else {
            user.getSubscribedFeeds().add(feed);
            userRepository.save(user);
            Long newFeedId = feed.getId();
            response = "{\"result\":\"subscribed\",\"id\":" + newFeedId + "}";
        }
        return response;
    }

    private Long getIdForBrandNewFeed(String url) throws IOException, FeedException {
        feedService.addNewFeed(url);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.getSubscribedFeeds().add(feedService.findFeedByUrl(url));
        userRepository.save(user);

        return feedService.findFeedByUrl(url).getId();
    }
}
