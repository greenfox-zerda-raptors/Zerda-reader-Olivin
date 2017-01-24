package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.Feed;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.utility.TempSyndFeedStorage;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by ${rudolfps} on 2017.01.18..
 */

@Component
@EnableScheduling
public class FeedService {

    FeedRepository feedRepo;

    @Autowired
    public FeedService(FeedRepository feedRepo) {
        this.feedRepo = feedRepo;
    }

    public void addNewFeed(String rssPath) throws IOException, FeedException {
        TempSyndFeedStorage storage = new TempSyndFeedStorage(rssPath);
        if (!isExist(storage)) {
            addAllEntries(storage);
        }
    }

    @Scheduled(fixedRate = 120000)
    public void updateAllFeeds() throws IOException, FeedException {
        for (long i = 1; i <= getNumberOfFeeds(); i++) {
            Feed feed = getFeed(i);
            String rssPath = feed.getRssPath();
            TempSyndFeedStorage storage = new TempSyndFeedStorage(rssPath);
            if (isUpdateNeeded(feed, storage.getSyndFeed())) {
                feed.updateEntries(storage.getSyndFeed());
                feed.setPubDate(LocalDateTime.ofInstant(storage.getSyndFeed().getPublishedDate().toInstant(), ZoneId.systemDefault()));
                updateFeed(feed);
            }
        }
    }

    private boolean isUpdateNeeded(Feed feed, SyndFeed syndFeed) {
        return !LocalDateTime.ofInstant(syndFeed.getPublishedDate().toInstant(), ZoneId.systemDefault()).isEqual(feed.getPubDate());
    }

    private void addAllEntries(TempSyndFeedStorage storage) {
        Feed feed = storage.convertToFeed();
        feedRepo.save(feed);
    }

    private boolean isExist(TempSyndFeedStorage tempSyndFeedStorage) {
        return feedRepo.findOneByRssPath(tempSyndFeedStorage.getRssPath()) != null;
    }

    public Feed getFeed(Long id) {
        return feedRepo.findOne(id);
    }

    private void updateFeed(Feed feed) {
        feedRepo.save(feed);
    }

    private long getNumberOfFeeds() {
        return feedRepo.count();
    }
}
