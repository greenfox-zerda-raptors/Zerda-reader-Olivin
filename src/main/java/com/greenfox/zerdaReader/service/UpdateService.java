package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.Feed;
import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.FeedsForUsers;
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
        this.feedRepository = feedRepository;
        this.feedItemRepository = feedItemRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void update() throws IOException, FeedException {
        log.info("update started");
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
                            feedItem.addNewFeedsForUsers(new FeedsForUsers(user, feedItem));
                            feedItemRepository.save(feedItem);
                        }
                    }
                }
                feed.setPubDate(LocalDateTime.ofInstant(storage.getSyndFeed().getPublishedDate().toInstant(), ZoneId.systemDefault()));
                feedRepository.save(feed);
            }
        }
        log.info("update stopped");
    }

    private LocalDateTime convertDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private boolean isUpdateNeeded(Feed feed, SyndFeed syndFeed) {
        return !LocalDateTime.ofInstant(syndFeed.getPublishedDate().toInstant(), ZoneId.systemDefault()).isEqual(feed.getPubDate());
    }
}
