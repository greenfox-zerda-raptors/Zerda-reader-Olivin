package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rita on 2017-01-19.
 */
@Component
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
            feedService.createNewFeedItem(feedReader.getSyndFeedStorageFromRssUrl(storage));
        }
    }

    public void updateAllFeeds() throws IOException, FeedException {
        List<String> rssLinks = feedService.getAllrssLinks();
        for (String rssLink : rssLinks) {
            feedsToUpdate.add(feedReader.getSyndFeedStorageFromRssUrl(new TempSyndFeedStorage(rssLink)));

        }
        for (TempSyndFeedStorage feed : feedsToUpdate) {
            updateFeed(feed, feedService.getFeedBasedOnTempSFStorage(feed));
        }
    }

    public void updateFeed(TempSyndFeedStorage tempSyndFeedStorage, Feed feed) {
        SyndFeed syndFeed = tempSyndFeedStorage.getSyndFeed();
        if (!feedService.setPubDateByDate(syndFeed.getPublishedDate()).equals(feed.getPubDate())) {
            for (SyndEntry se : syndFeed.getEntries()) {
                if (!feedService.setPubDateByDate(se.getPublishedDate()).equals(feed.getPubDate())) {
                    feedService.createNewFeedItem(tempSyndFeedStorage);
                }
            }
            feed.setPubDate(feedService.setPubDateByDate(syndFeed.getPublishedDate()));
        }
    }
}
