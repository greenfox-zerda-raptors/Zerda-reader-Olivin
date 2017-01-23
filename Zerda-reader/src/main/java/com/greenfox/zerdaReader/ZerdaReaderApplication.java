package com.greenfox.zerdaReader;

import com.greenfox.zerdaReader.domain.Feed;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.UserRepository;
import com.greenfox.zerdaReader.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class ZerdaReaderApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ZerdaReaderApplication.class, args);
    }

    @Autowired
    FeedService service;

    //needed to initialize user
    @Autowired
    UserRepository userRepository;
    //needed to initialize user-feed subscription
    @Autowired
    FeedRepository feedRepository;


    @Override
    public void run(String... strings) throws Exception {
        service.addNewFeed("http://index.hu/24ora/rss/");
        service.addNewFeed("http://444.hu/feed");
        service.addNewFeed("http://444.hu/feed");
        service.updateAllFeeds();

        User testUser = new User(1);
        userRepository.save(testUser);
        testUser = userRepository.findOne(1L);
        testUser.getSubscribedFeeds().add(service.getFeed(1L));
        testUser.getSubscribedFeeds().add(service.getFeed(2L));
        Feed feed = service.getFeed(1L);
        feed.setSubscribedUsers(new ArrayList<>(Arrays.asList(testUser)));
        Feed feed2 = service.getFeed(2L);
        feed2.setSubscribedUsers(new ArrayList<>(Arrays.asList(testUser)));
        feedRepository.save(feed);
        feedRepository.save(feed2);
        userRepository.save(testUser);
    }
}

