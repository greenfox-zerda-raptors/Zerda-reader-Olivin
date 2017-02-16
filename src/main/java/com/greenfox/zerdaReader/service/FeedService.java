package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.Feed;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.utility.TempSyndFeedStorage;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by ${rudolfps} on 2017.01.18..
 */

@Component
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

    public Feed findFeedByUrl(String url){
        return feedRepo.findOneByRssPath(url);
    }

}
