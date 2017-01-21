package com.zerdareader;

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
 * Created by Rita on 2017-01-19.
 */
@Component
@EnableScheduling
public class FeedManager {

    FeedService feedService;

    @Autowired
    public FeedManager(FeedService feedService) {
        this.feedService = feedService;
    }

    public void addNewFeed(String rssPath) throws IOException, FeedException {
        TempSyndFeedStorage storage = new TempSyndFeedStorage(rssPath);
        if (!feedService.isExist(storage)) {
            feedService.addAllEntries(storage);
        }
    }

    @Scheduled(fixedRate = 120000)
    public void updateAllFeeds() throws IOException, FeedException {
        // option1 : repeated calls to the database
        for (long i = 1; i <= feedService.getNumberOfFeeds(); i++) {
            Feed feed = feedService.getFeed(i);
            String rssPath = feed.getRssPath();
            TempSyndFeedStorage storage = new TempSyndFeedStorage(rssPath);
            if (isUpdateNeeded(feed, storage.getSyndFeed())) {
                feed.updateEntries(storage.getSyndFeed());
                feedService.updateFeed(feed);
            }
        }

        //option2 : requires more memory to store the lists + repeated calls to the DB when feed is saved
//        List<TempSyndFeedStorage> feedsToUpdate = new ArrayList<>();
//        List<String> rssLinks = feedService.getAllRssLinks();
//        for (String rssLink : rssLinks) {
//            feedsToUpdate.add(feedReader.getSyndFeedStorageFromRssUrl(new TempSyndFeedStorage(rssLink)));
//        }
//        for (TempSyndFeedStorage feedStorage : feedsToUpdate) {
//            Feed feed = feedService.getFeedBasedOnTempSFStorage(feedStorage);
//            feed.updateEntries(feedStorage.getSyndFeed());
//            feedService.updateFeed(feed);
//        }
    }

    private boolean isUpdateNeeded(Feed feed, SyndFeed syndFeed) {
        return !LocalDateTime.ofInstant(syndFeed.getPublishedDate().toInstant(), ZoneId.systemDefault()).isEqual(feed.getPubDate());
    }
}
