package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rita on 2017-01-19.
 */
@Component
@EnableScheduling
public class FeedUpdater {

    FeedReader feedReader;
    FeedService feedService;
    List<TempSyndFeedStorage> feedsToUpdate;

    @Autowired
    public FeedUpdater(FeedReader feedReader, FeedService feedService) {
        this.feedReader = feedReader;
        this.feedService = feedService;
        feedsToUpdate = new ArrayList<>();
    }

    public void addNewFeed(String rssPath) throws IOException, FeedException {
        TempSyndFeedStorage storage = new TempSyndFeedStorage(rssPath);
        if (!feedService.isExist(storage)) {
            feedService.addAllEntries(feedReader.getSyndFeedStorageFromRssUrl(storage));
        }
    }

    @Scheduled(fixedRate = 120000)
    public void updateAllFeeds() throws IOException, FeedException {
        List<String> rssLinks = feedService.getAllrssLinks();
        for (String rssLink : rssLinks) {
            feedsToUpdate.add(feedReader.getSyndFeedStorageFromRssUrl(new TempSyndFeedStorage(rssLink)));
        }
        for (TempSyndFeedStorage feed : feedsToUpdate) {
            updateFeed(feed, feedService.getFeedBasedOnTempSFStorage(feed));
        }
        feedsToUpdate.clear();
    }

    public void updateFeed(TempSyndFeedStorage tempSyndFeedStorage, Feed feed) {
        SyndFeed syndFeed = tempSyndFeedStorage.getSyndFeed();
        if (!feedService.convertDate(syndFeed.getPublishedDate()).isEqual(feed.getPubDate())) {
            for (SyndEntry se : syndFeed.getEntries()) {
                if (feedService.convertDate(se.getPublishedDate()).isAfter(feed.getPubDate())) {
                    feedService.addNewEntry(se, feed);
                }
            }
            feed.setPubDate(feedService.convertDate(syndFeed.getPublishedDate()));
            feedService.updateFeed(feed);
        }
    }
}
