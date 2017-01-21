package com.zerdareader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ${rudolfps} on 2017.01.18..
 */

@Component
public class FeedService {

    FeedRepository feedRepo;
    FeedItemRepository feedItemRepo;

    @Autowired
    public FeedService(FeedRepository feedRepo, FeedItemRepository feedItemRepo) {
        this.feedRepo = feedRepo;
        this.feedItemRepo = feedItemRepo;
    }

    public void addAllEntries(TempSyndFeedStorage storage) {
        Feed feed = storage.convertToFeed();
        feedRepo.save(feed);
    }

    public FeedItem getFeedItem(Long id) {
        return feedItemRepo.findOne(id);
    }

    public boolean isExist(TempSyndFeedStorage tempSyndFeedStorage) {
        return feedRepo.findOneByRssPath(tempSyndFeedStorage.getRssPath()) != null;
    }

    public Feed getFeed(Long id) {
        return feedRepo.findOne(id);
    }

    public void updateFeed(Feed feed) {
        feedRepo.save(feed);
    }

    public long getNumberOfFeeds() {
        return feedRepo.count();
    }
}
