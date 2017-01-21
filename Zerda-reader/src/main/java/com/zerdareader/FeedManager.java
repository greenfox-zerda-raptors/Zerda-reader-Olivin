package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rita on 2017-01-19.
 */
@Component
@EnableScheduling
public class FeedManager {

    FeedReader feedReader;
    FeedService feedService;

    @Autowired
    public FeedManager(FeedReader feedReader, FeedService feedService) {
        this.feedReader = feedReader;
        this.feedService = feedService;
    }

    public void addNewFeed(String rssPath) throws IOException, FeedException {
        TempSyndFeedStorage storage = new TempSyndFeedStorage(rssPath);
        if (!feedService.isExist(storage)) {
            feedService.addAllEntries(feedReader.getSyndFeedStorageFromRssUrl(storage));
        }
    }

    @Scheduled(fixedRate = 120000)
    public void updateAllFeeds() throws IOException, FeedException {
        List<TempSyndFeedStorage> feedsToUpdate = new ArrayList<>();
        List<String> rssLinks = feedService.getAllRssLinks();
        for (String rssLink : rssLinks) {
            feedsToUpdate.add(feedReader.getSyndFeedStorageFromRssUrl(new TempSyndFeedStorage(rssLink)));
        }
        for (TempSyndFeedStorage feed : feedsToUpdate) {
            updateFeed(feed, feedService.getFeedBasedOnTempSFStorage(feed));
        }
    }

    public void updateFeed(TempSyndFeedStorage tempSyndFeedStorage, Feed feed) {
        SyndFeed syndFeed = tempSyndFeedStorage.getSyndFeed();
        if (!LocalDateTime.ofInstant(syndFeed.getPublishedDate().toInstant(), ZoneId.systemDefault()).isEqual(feed.getPubDate())) {
            for (SyndEntry se : syndFeed.getEntries()) {
                if (LocalDateTime.ofInstant(se.getPublishedDate().toInstant(), ZoneId.systemDefault()).isAfter(feed.getPubDate())) {
                    feed.addNewEntry(se);
                }
            }
            feed.setPubDate(LocalDateTime.ofInstant(syndFeed.getPublishedDate().toInstant(), ZoneId.systemDefault()));
            feedService.updateFeed(feed);
        }
    }
}
