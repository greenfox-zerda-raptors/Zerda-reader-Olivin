package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rita on 2017-01-19.
 */
public class FeedUpdater {

    FeedReader feedReader;
    FeedService feedService;
    List<TempSyndFeedStorage> feedsToUpdate;

    @Autowired
    public FeedUpdater(FeedReader feedReader, FeedService feedService) {
        this.feedReader = feedReader;
        this.feedService = feedService;
        feedsToUpdate = new ArrayList<>()
        List<String> rssLinks = feedService.getAllrssLinks();
        for (String rssLink : rssLinks) {
            feedsToUpdate.add(new TempSyndFeedStorage(rssLink));
        }
    }

    public boolean isExist(TempSyndFeedStorage) {

    }

    public void updateFeeds(TempSyndFeedStorage tempSyndFeedStorage, Feed feed) {
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
