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
    TestUserRepository testUserRepo;
    @Autowired
    FeedRepository feedRepo;
    @Autowired
    FeedItemRepository feedItemRepo;


    @Override
    public void run(String... strings) throws Exception {
        updater.addNewFeed("http://index.hu/24ora/rss/");
        updater.addNewFeed("http://444.hu/feed");
        updater.addNewFeed("http://444.hu/feed");
        updater.updateAllFeeds();

        TestUser testUser = new TestUser(1);
        testUserRepo.save(testUser);
        testUser= testUserRepo.findOne(1L);
//        testUser.getSubscribedFeeds().add(feedRepo.findOne(1L));
//        testUser.getSubscribedFeeds().add(feedRepo.findOne(2L));
//        Feed feed = feedRepo.findOne(1L);
//        feed.setSubscribedUsers(new ArrayList<>(Arrays.asList(testUser)));
//        Feed feed2 = feedRepo.findOne(2L);
//        feed2.setSubscribedUsers(new ArrayList<>(Arrays.asList(testUser)));
//        feedRepo.save(feed);
//        feedRepo.save(feed2);
//        for (Feed f:testUser.getSubscribedFeeds()
//             ) {
        Feed f = feedRepo.findOne(1L);
            for (FeedItem fi:f.getEntries()
                 ) {
                testUser.getSubscribedFeedItems().add(fi);
                fi.setSubscribedUsers(new ArrayList<>(Arrays.asList(testUser)));
//                feedItemRepo.save(fi);
            }
//        }

        testUserRepo.save(testUser);

    }
}
