package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.net.URL;
import java.util.Iterator;
import java.util.List;


/**
 * Created by ${rudolfps} on 2017.01.18..
 */



public class FeedReader {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://index.hu/24ora/rss/");
       // HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));
        List entries = feed.getEntries();
        Iterator itEntries = entries.iterator();

        while (itEntries.hasNext()) {
            SyndEntry entry = (SyndEntry) itEntries.next();
            System.out.println("Title: " + entry.getTitle());
            System.out.println("Link: " + entry.getLink());
            System.out.println("Author: " + entry.getAuthor());
            System.out.println("Publish Date: " + entry.getPublishedDate());
            System.out.println("Description: " + entry.getDescription().getValue());
            System.out.println();
        }
    }
}