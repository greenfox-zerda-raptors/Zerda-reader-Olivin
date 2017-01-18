package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ${rudolfps} on 2017.01.18..
 */

@Component
public class FeedService {

    FeedItemRepository feedRepo;
    FeedReader feedReader;
    SyndFeed tempFeed;
    Feed output;


    @Autowired
    public FeedService(FeedItemRepository feedRepo, FeedReader feedReader) {
        this.feedRepo = feedRepo;
        this.feedReader = feedReader;
        tempFeed = feedReader.getFeed();
        output = new Feed();
    }

    public Feed createNewFeed() {
        output.setTitle(tempFeed.getTitle());
        output.setLink(tempFeed.getLink());
        output.setDescription(tempFeed.getDescription());
        output.setLanguage(tempFeed.getLanguage());
        output.setCopyright(tempFeed.getCopyright());

        return output;
    }

    public void createNewFeedItem() {
        List<SyndEntry> tempEntries = tempFeed.getEntries();
        for (SyndEntry te : tempEntries) {
            FeedItem feedItem = new FeedItem();
            feedItem.setTitle(te.getTitle());
            feedItem.setDescription(te.getDescription().getValue());
            feedItem.setLink(te.getLink());
            feedItem.setAuthor(te.getAuthor());
            feedItem.setFeed(createNewFeed());
            feedRepo.save(feedItem);
        }

    }

    public FeedItem getFeedItem(Long id) {
        FeedItem feedItem = feedRepo.findOne(id);
        return feedItem;
    }
}
