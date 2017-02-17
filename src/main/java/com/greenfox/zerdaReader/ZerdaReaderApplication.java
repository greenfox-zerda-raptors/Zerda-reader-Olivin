package com.greenfox.zerdaReader;

import com.greenfox.zerdaReader.domain.Feed;
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
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@ComponentScan("com.greenfox.zerdaReader")

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
        feedService.addNewFeed("http://index.hu/24ora/rss/");

//        a feedidkat trukkosen kell osszeszedni, ezek kozul elekerjuk az elsot
        User testUser = new User("email", "arsitenrastie");
        userRepository.save(testUser);

        Long id = feedRepository.getAllFeedId().get(0);
        Long id2 = feedRepository.getAllFeedId().get(1);

//        azzal előszedjük a feedet
        Feed feed = feedService.getFeed(id);
        Feed feed2 = feedService.getFeed(id2);
//        hozzáadjuk a tesztuserhez
        testUser.getSubscribedFeeds().add(feed);
        testUser.getSubscribedFeeds().add(feed2);
//        a testusert is hozzáadjuk a feed-hez, így most mindkét ID a helyén van
        feed.getSubscribedUsers().add(testUser);
        feed2.getSubscribedUsers().add(testUser);
//        mentünk mindkét oldalon
        feedRepository.save(feed);
        feedRepository.save(feed2);
        userRepository.save(testUser);

        updateService.update();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes(){};
    }
}

