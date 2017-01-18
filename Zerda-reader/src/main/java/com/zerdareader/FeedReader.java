package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;


/**
 * Created by ${rudolfps} on 2017.01.18..
 */

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FeedReader {

    URL url;
    SyndFeedInput input;
    SyndFeed feed;
    List<SyndEntry> entries;

    public FeedReader(URL rssUrl) throws IOException, FeedException {
        url = rssUrl;
        input = new SyndFeedInput();
        feed = input.build(new XmlReader(url));
        entries = feed.getEntries();
    }


//    public static void main(String[] args) throws Exception {
//        URL url = new URL("http://index.hu/24ora/rss/");
//        // HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
//        SyndFeedInput input = new SyndFeedInput();
//        SyndFeed feed = input.build(new XmlReader(url));
//        List entries = feed.getEntries();
//        Iterator itEntries = entries.iterator();
//
//        while (itEntries.hasNext()) {
//            SyndEntry entry = (SyndEntry) itEntries.next();
//            System.out.println("Title: " + entry.getTitle());
//            System.out.println("Link: " + entry.getLink());
//            System.out.println("Author: " + entry.getAuthor());
//            System.out.println("Publish Date: " + entry.getPublishedDate());
//            System.out.println("Description: " + entry.getDescription().getValue());
//            System.out.println();
//        }
//    }
}