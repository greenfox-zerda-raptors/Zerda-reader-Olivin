package com.zerdareader;

import com.rometools.rome.feed.synd.SyndFeed;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rita on 2017-01-19.
 */
public class TempSyndFeedStorage {
    private SyndFeed syndFeed;
    private String urlPath;

    public TempSyndFeedStorage(String urlPath) {
        this.urlPath = urlPath;
    }

    public URL createUrl() throws MalformedURLException {
        return new URL(urlPath);
    }

    public void setSyndFeed(SyndFeed syndFeed) {
        this.syndFeed = syndFeed;
    }

    public SyndFeed getSyndFeed() {
        return syndFeed;
    }
}
