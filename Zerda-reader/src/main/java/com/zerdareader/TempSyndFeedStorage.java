package com.zerdareader;

import com.rometools.rome.feed.synd.SyndFeed;
import lombok.Data;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rita on 2017-01-19.
 */

@Data
public class TempSyndFeedStorage {
    private SyndFeed syndFeed;
    private String rssPath;

    public TempSyndFeedStorage(String urlPath) {
        this.rssPath = urlPath;
    }

    public URL createUrl() throws MalformedURLException {
        return new URL(rssPath);
    }

    public void setSyndFeed(SyndFeed syndFeed) {
        this.syndFeed = syndFeed;
    }

    public SyndFeed getSyndFeed() {
        return syndFeed;
    }
}
