package com.greenfox.zerdaReader;

import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import com.greenfox.zerdaReader.repository.UserRepository;
import com.greenfox.zerdaReader.service.FeedService;
import com.greenfox.zerdaReader.service.FeedsForUsersService;
import com.greenfox.zerdaReader.service.UpdateService;
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
    @Autowired
    UpdateService updateService;

    @Override
    public void run(String... strings) throws Exception {
//        feedService.addNewFeed("http://index.hu/24ora/rss/");
//        feedService.addNewFeed("http://444.hu/feed");
//        feedService.addNewFeed("http://hvg.hu/rss");
//        feedService.addNewFeed("http://24.hu/feed/");
        feedService.addNewFeed("http://lorem-rss.herokuapp.com/feed?unit=second&interval=30");

        User testUser = new User(1234);
        userRepository.save(testUser);
        Long id = userRepository.getAllUserId().get(0);
        testUser = userRepository.findOne(id);

        id = feedRepository.getAllFeedId().get(0);
        testUser.getSubscribedFeeds().add(feedService.getFeed(id));
//        testUser.getSubscribedFeeds().add(feedService.getFeed(3L));
//        testUser.getSubscribedFeeds().add(feedService.getFeed(5L));
//        feedsForUsersService.populateFeedsForUsers(testUser);
//        userRepository.save(testUser);
        updateService.update();

//        testUser = new User(567246);
//        userRepository.save(testUser);
//        testUser = userRepository.findOne(2L);
//
//        testUser.getSubscribedFeeds().add(feedService.getFeed(2L));
//        testUser.getSubscribedFeeds().add(feedService.getFeed(4L));
//        feedsForUsersService.populateFeedsForUsers(testUser);
//        userRepository.save(testUser);
    }
}

