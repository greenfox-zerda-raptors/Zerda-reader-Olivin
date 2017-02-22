package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.Feed;
import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.FeedItemsForUsers;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.FeedItemRepository;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.utility.TempSyndFeedStorage;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zoloe on 2017. 01. 31..
 */

@EnableScheduling
@Component
@Log
public class UpdateService {

    FeedItemRepository feedItemRepository;
    FeedRepository feedRepository;

    @Autowired
    public UpdateService(FeedItemRepository feedItemRepository, FeedRepository feedRepository) {
        this.feedItemRepository = feedItemRepository;
        this.feedRepository = feedRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void update() throws IOException, FeedException {
        log.info("update started");
        ArrayList<FeedItem> feedItemsList = new ArrayList<FeedItem>();
        ArrayList<Feed> feedsList = new ArrayList<Feed>();
        try {
            for (long i : feedRepository.getAllFeedId()) {
                Feed feed = feedRepository.findOne(i);
                String rssPath = feed.getRssPath();
                TempSyndFeedStorage storage = new TempSyndFeedStorage(rssPath);
                if (isUpdateNeeded(feed, storage.getSyndFeed())) {
                    for (SyndEntry se : storage.getSyndFeed().getEntries()) {
                        if (convertDate(se.getPublishedDate()).isAfter(feed.getPubDate())) {
                            FeedItem feedItem = new FeedItem();
                            feedItem.setFields(se, feed);
                            for (User user : feed.getSubscribedUsers()) {
                                feedItem.addNewFeedItemsForUsers(new FeedItemsForUsers(user, feedItem));
                                feedItemsList.add(feedItem);
                            }
                        }
                    }
                    feed.setPubDate(convertDate(storage.getSyndFeed().getPublishedDate()));
                    feedsList.add(feed);
                }
            }
        } catch (OutOfMemoryError error) {
            log.info("OutOfMemoryError" + "\n" + error.getMessage() + "\n" + error.getStackTrace());
            log.info("Exiting without saving");
            return;
        }
        feedItemRepository.save(feedItemsList);
        feedRepository.save(feedsList);
        log.info("update stopped");
    }

    public LocalDateTime convertDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public boolean isUpdateNeeded(Feed feed, SyndFeed syndFeed) {
        return !LocalDateTime.ofInstant(syndFeed.getPublishedDate().toInstant(), ZoneId.systemDefault()).isEqual(feed.getPubDate());
    }

    public void updateFeedForUserByUrl(String url, User user) throws IOException, FeedException{
        log.info("update for feed:" + url + "started");
        ArrayList<FeedItem> feedItemsList = new ArrayList<FeedItem>();
        ArrayList<Feed> feedsList = new ArrayList<Feed>();
        try {
                Feed feed = feedRepository.findOneByRssPath(url);
                TempSyndFeedStorage storage = new TempSyndFeedStorage(url);
                if (isUpdateNeeded(feed, storage.getSyndFeed())) {
                    for (SyndEntry se : storage.getSyndFeed().getEntries()) {
                        if (convertDate(se.getPublishedDate()).isAfter(feed.getPubDate())) {
                            FeedItem feedItem = new FeedItem();
                            feedItem.setFields(se, feed);
                                feedItem.addNewFeedItemsForUsers(new FeedItemsForUsers(user, feedItem));
                                feedItemsList.add(feedItem);
                        }
                    }
                    feed.setPubDate(convertDate(storage.getSyndFeed().getPublishedDate()));
                    feedsList.add(feed);
                }
        } catch (OutOfMemoryError error) {
            log.info("OutOfMemoryError"+ "\n" + error.getMessage()+ "\n" +error.getStackTrace());
            log.info("Exiting without saving");
            return;
        }
        feedItemRepository.save(feedItemsList);
        feedRepository.save(feedsList);
        log.info("update stopped");
    }
}

