package com.zerdareader;

import com.zerdareader.Model.TestUser;
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
    FeedUpdater updater;

    @Autowired
    FeedService feedService;
    @Autowired
    TestUserRepository testUserRepository;


    @Override
    public void run(String... strings) throws Exception {
        TestUser testUser = new TestUser(1);
        testUser.getSubscribedFeeds().add(feedService.getFeed(1L));
        testUser.getSubscribedFeeds().add(feedService.getFeed(2L));
        Feed feed = feedService.getFeed(1L);
        feed.setSubscribedUsers(new ArrayList<>(Arrays.asList(testUser)));
        Feed feed2 = feedService.getFeed(2L);
        feed2.setSubscribedUsers(new ArrayList<>(Arrays.asList(testUser)));
        feedService.updateFeed(feed);
        feedService.updateFeed(feed2);

        testUserRepository.save(testUser);
        updater.addNewFeed("http://index.hu/24ora/rss/");
        updater.addNewFeed("http://444.hu/feed");
        updater.addNewFeed("http://444.hu/feed");
        updater.updateAllFeeds();
    }
}
