package com.greenfox.zerdaReader.utility;

import com.greenfox.zerdaReader.domain.Feed;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Rita on 2017-01-19.
 */

@Getter
public class TempSyndFeedStorage {

    private SyndFeed syndFeed;
    private String rssPath;

    public TempSyndFeedStorage(String rssPath) throws IOException, FeedException {
        this.rssPath = rssPath;
        populateSyndFeed();
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

     void populateSyndFeed() throws IOException, FeedException {
        FeedReader reader = new FeedReader();
        syndFeed = reader.convertRssFeed(new URL(rssPath));
    }
}
