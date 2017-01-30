package com.greenfox.zerdaReader.utility;

import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.service.FeedService;
import com.greenfox.zerdaReader.service.FeedsForUsersService;
import com.greenfox.zerdaReader.service.UserService;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by zoloe on 2017. 01. 30..
 */

@EnableScheduling
@Component
public class Updater {

    FeedService feedService;
    FeedsForUsersService feedsForUsersService;
    UserService userService;

    @Autowired
    public Updater(FeedService feedService, FeedsForUsersService feedsForUsersService, UserService userService) {
        this.feedService = feedService;
        this.feedsForUsersService = feedsForUsersService;
        this.userService = userService;
    }

    @Scheduled(fixedRate = 120000)
    public void update() throws IOException, FeedException {
        feedService.updateAllFeeds();
        for (User user:userService.getAllUsers()) {
            feedsForUsersService.populateFeedsForUsers(user);
        }
    }



}
