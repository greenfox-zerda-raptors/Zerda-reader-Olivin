package com.zerdareader;

import org.springframework.beans.factory.annotation.Autowired;

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
        
    }

}
