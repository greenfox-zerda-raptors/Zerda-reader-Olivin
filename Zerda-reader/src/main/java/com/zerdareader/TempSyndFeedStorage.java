package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import lombok.Data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Rita on 2017-01-19.
 */

@Data
public class TempSyndFeedStorage {
    private SyndFeed syndFeed;
    private String rssPath;

    public TempSyndFeedStorage(String rssPath) throws IOException, FeedException {
        this.rssPath = rssPath;
        populateSyndFeed();
    }

    public URL createUrl() throws MalformedURLException {
        return new URL(rssPath);
    }

    public Feed convertToFeed() {
        Feed result = new Feed();
        result.setFields(this);
        List<SyndEntry> tempEntries = syndFeed.getEntries();
        for (SyndEntry te : tempEntries) {
            result.addNewEntry(te);
        }
        return result;
    }
    
    public void populateSyndFeed() throws IOException, FeedException {
        FeedReader reader = new FeedReader();
        syndFeed = reader.convertRssFeed(createUrl());
    }
}
