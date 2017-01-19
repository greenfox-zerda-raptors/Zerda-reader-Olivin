package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntry;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${rudolfps} on 2017.01.18..
 */

@Component
@Getter
public class FeedService {

    FeedRepository feedRepo;
    FeedItemRepository feedItemRepo;

    @Autowired
    public FeedService(FeedRepository feedRepo, FeedItemRepository feedItemRepo, FeedReader feedReader) {
        this.feedRepo = feedRepo;
        this.feedItemRepo = feedItemRepo;
    }

    private Feed createNewFeed(TempSyndFeedStorage storage) {
        Feed output = new Feed();
        output.setTitle(storage.getSyndFeed().getTitle());
        output.setLink(storage.getSyndFeed().getLink());
        output.setDescription(storage.getSyndFeed().getDescription());
        output.setLanguage(storage.getSyndFeed().getLanguage());
        output.setCopyright(storage.getSyndFeed().getCopyright());
        output.setRssPath(storage.getRssPath());
        return output;
    }

    public void createNewFeedItem(TempSyndFeedStorage storage) {
        Feed feed = createNewFeed(storage);
        List<SyndEntry> tempEntries = storage.getSyndFeed().getEntries();
        List<FeedItem> entries = new ArrayList<>();
        for (SyndEntry te : tempEntries) {
            FeedItem feedItem = new FeedItem();
            feedItem.setTitle(te.getTitle());
            feedItem.setDescription(te.getDescription().getValue());
            feedItem.setLink(te.getLink());
            feedItem.setAuthor(te.getAuthor());
            feedItem.setFeed(feed);
            entries.add(feedItem);
        }
        feed.setEntries(entries);
        feedRepo.save(feed);
        storage = null;
    }

    public FeedItem getFeedItem(Long id) {
        FeedItem feedItem = feedItemRepo.findOne(id);
        return feedItem;
    }

    public boolean isExist(TempSyndFeedStorage tempSyndFeedStorage) {
        if (feedRepo.findOneByrssPath(tempSyndFeedStorage.getRssPath()) != null){
        }return true;
    }



}
