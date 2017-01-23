package com.greenfox.zerdaReader;

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
    FeedService service;

    @Override
    public void run(String... strings) throws Exception {
        service.addNewFeed("http://index.hu/24ora/rss/");
        service.addNewFeed("http://444.hu/feed");
        service.addNewFeed("http://444.hu/feed");
        service.updateAllFeeds();
    }
}
