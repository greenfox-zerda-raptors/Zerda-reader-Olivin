package com.greenfox.zerdaReader.utility;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;


/**
 * Created by ${rudolfps} on 2017.01.18..
 */

class FeedReader {

    SyndFeed convertRssFeed(URL url) throws IOException, FeedException {
        SyndFeedInput input = new SyndFeedInput();
        return input.build(new XmlReader(url));
    }
}