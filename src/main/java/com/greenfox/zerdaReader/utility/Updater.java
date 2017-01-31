package com.greenfox.zerdaReader.utility;

import com.greenfox.zerdaReader.repository.FeedItemRepository;
import com.greenfox.zerdaReader.service.FeedService;
import com.greenfox.zerdaReader.service.FeedsForUsersService;
import com.greenfox.zerdaReader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by zoloe on 2017. 01. 30..
 */

@EnableScheduling
@Component
@Transactional
public class Updater {
    final int UPDATEFREQUENCY = 120000;
    FeedService feedService;
    FeedsForUsersService feedsForUsersService;
    UserService userService;
    FeedItemRepository feedItemRepository;

    @Autowired
    public Updater(FeedService feedService, FeedsForUsersService feedsForUsersService, UserService userService) {
        this.feedService = feedService;
        this.feedsForUsersService = feedsForUsersService;
        this.userService = userService;
    }

//    @Scheduled(fixedRate = UPDATEFREQUENCY)
//    public void update() throws IOException, FeedException {
//        List<FeedItem> updatedFeedItems = feedService.updateAllFeeds();
//        for (FeedItem fi : updatedFeedItems) {
//            for (User user : fi.getFeed().getSubscribedUsers()) {
////                if (fi.getPubDate().isAfter(f.getPubDate().minusMinutes((UPDATEFREQUENCY/60000)+1)))
//                feedsForUsersService.addFeedsForUsers(user, fi);
//            }
//        }
//    }
}
