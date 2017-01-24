package com.greenfox.zerdaReader;

import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import com.greenfox.zerdaReader.repository.UserRepository;
import com.greenfox.zerdaReader.service.FeedService;
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

    @Override
    public void run(String... strings) throws Exception {
        feedService.addNewFeed("http://index.hu/24ora/rss/");
        feedService.addNewFeed("http://444.hu/feed");
        feedService.addNewFeed("http://444.hu/feed");
        feedService.updateAllFeeds();

//        FeedsForUsersRepository feedsForUsers = new FeedsForUsersRepository();
//        readStatusAndStarredRepository.save(feedsForUsers);
//        feedsForUsers = readStatusAndStarredRepository.findOne(1L);

        User testUser = new User(1);
        userRepository.save(testUser);
        testUser = userRepository.findOne(1L);

        testUser.getSubscribedFeeds().add(feedService.getFeed(1L));
        testUser.getSubscribedFeeds().add(feedService.getFeed(2L));

        com.greenfox.zerdaReader.domain.FeedsForUsers feedsForUsers = new com.greenfox.zerdaReader.domain.FeedsForUsers(true,false,testUser,feedService.getFeed(1L).getEntries().get(1));

        testUser.getFeedsForUsers().add(feedsForUsers);

        userRepository.save(testUser);


    }
}

