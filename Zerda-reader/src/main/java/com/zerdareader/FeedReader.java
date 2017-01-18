package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * Created by ${rudolfps} on 2017.01.18..
 */

@Component
@AllArgsConstructor
@Getter
public class FeedReader {

    URL url;
    SyndFeedInput input;
    SyndFeed feed;
    List<SyndEntry> entries;

    public FeedReader() throws MalformedURLException, IOException, FeedException {
        url = new URL("http://444.hu/feed");
        input = new SyndFeedInput();
        feed = input.build(new XmlReader(url));
        entries = feed.getEntries();
    }
}