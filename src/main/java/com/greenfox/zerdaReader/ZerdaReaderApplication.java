package com.greenfox.zerdaReader;

import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import com.greenfox.zerdaReader.repository.UserRepository;
import com.greenfox.zerdaReader.service.FeedService;
import com.greenfox.zerdaReader.service.FeedsForUsersService;
import com.greenfox.zerdaReader.utility.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZerdaReaderApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ZerdaReaderApplication.class, args);
    }

    @Autowired
    FeedService feedService;

    @Autowired
    Updater updater;

    //needed to initialize user
    @Autowired
    UserRepository userRepository;
    //needed to initialize user-feed subscription
    @Autowired
    FeedRepository feedRepository;
    @Autowired
    FeedsForUsersRepository feedsForUsersRepository;
    @Autowired
    FeedsForUsersService feedsForUsersService;

    @Override
    public void run(String... strings) throws Exception {
        feedService.addNewFeed("http://index.hu/24ora/rss/");
        feedService.addNewFeed("http://444.hu/feed");
        feedService.addNewFeed("http://hvg.hu/rss");
        feedService.addNewFeed("http://24.hu/feed/");

//         bocs Rita

//        feedService.updateAllFeeds();

//        User testUser = new User(1234);
//        userRepository.save(testUser);
//        testUser = userRepository.findOne(1L);
//
//        testUser.getSubscribedFeeds().add(feedService.getFeed(1L));
//        testUser.getSubscribedFeeds().add(feedService.getFeed(3L));
//        feedsForUsersService.populateFeedsForUsers(testUser);
//        userRepository.save(testUser);
//
//        testUser = new User(567246);
//        userRepository.save(testUser);
//        testUser = userRepository.findOne(2L);
//
//        testUser.getSubscribedFeeds().add(feedService.getFeed(2L));
//        testUser.getSubscribedFeeds().add(feedService.getFeed(4L));
//        feedsForUsersService.populateFeedsForUsers(testUser);
//        userRepository.save(testUser);
//
//        feedsForUsersService.populateFeedsForUsers(testUser);


    }
}

