package com.zerdareader;

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
    private FeedItemRepository repo;

    @Autowired
    FeedUpdater updater;

    @Override
    public void run(String... strings) throws Exception {
        repo.save(new FeedItem("author", "description", "link", "title"));
        updater.addNewFeed("http://index.hu/24ora/rss/");
        updater.addNewFeed("http://444.hu/feed");
        updater.addNewFeed("http://444.hu/feed");
    }
}
