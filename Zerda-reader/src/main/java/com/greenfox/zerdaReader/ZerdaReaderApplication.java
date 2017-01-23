package com.greenfox.zerdaReader;

import com.greenfox.zerdaReader.domain.Feed;
import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.ReadStatusAndStarred;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.ReadStatusAndStarredRepository;
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
    FeedService feedService;

    //needed to initialize user
    @Autowired
    UserRepository userRepository;
    //needed to initialize user-feed subscription
    @Autowired
    FeedRepository feedRepository;
    @Autowired
    ReadStatusAndStarredRepository readStatusAndStarredRepository;

    @Override
    public void run(String... strings) throws Exception {
        feedService.addNewFeed("http://index.hu/24ora/rss/");
        feedService.addNewFeed("http://444.hu/feed");
        feedService.addNewFeed("http://444.hu/feed");
        feedService.updateAllFeeds();

//        ReadStatusAndStarred readStatusAndStarred = new ReadStatusAndStarred();
//        readStatusAndStarredRepository.save(readStatusAndStarred);
//        readStatusAndStarred = readStatusAndStarredRepository.findOne(1L);

        User testUser = new User(1);
        userRepository.save(testUser);
        testUser = userRepository.findOne(1L);

        testUser.getSubscribedFeeds().add(feedService.getFeed(1L));
        testUser.getSubscribedFeeds().add(feedService.getFeed(2L));

        ReadStatusAndStarred readStatusAndStarred = new ReadStatusAndStarred(true,false,testUser,feedService.getFeed(1L).getEntries().get(1));

        testUser.getReadStatusAndStarred().add(readStatusAndStarred);

        userRepository.save(testUser);


    }
}

