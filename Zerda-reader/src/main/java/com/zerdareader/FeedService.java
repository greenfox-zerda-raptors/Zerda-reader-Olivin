package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntry;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
        output.setRssPath(storage.getRssPath());
        output.setTitle(storage.getSyndFeed().getTitle());
        output.setLink(storage.getSyndFeed().getLink());
        output.setDescription(storage.getSyndFeed().getDescription());
        output.setLanguage(storage.getSyndFeed().getLanguage());
        output.setCopyright(storage.getSyndFeed().getCopyright());
        output.setPubDate(setPubDateByDate(storage.getSyndFeed().getPublishedDate()));
        return output;
    }

    public void createNewFeedItem(TempSyndFeedStorage storage) {
        Feed feed;
        if (!isExist(storage)) {
            feed = createNewFeed(storage);
        } else {
            feed = feedRepo.findOneByRssPath(storage.getRssPath());
        }

        List<SyndEntry> tempEntries = storage.getSyndFeed().getEntries();
        List<FeedItem> entries = new ArrayList<>();
        for (SyndEntry te : tempEntries) {
            FeedItem feedItem = new FeedItem();
            feedItem.setTitle(te.getTitle());
            feedItem.setDescription(te.getDescription().getValue());
            feedItem.setLink(te.getLink());
            feedItem.setAuthor(te.getAuthor());
            feedItem.setFeed(feed);
            feedItem.setPubDate(setPubDateByDate(te.getPublishedDate()));
            entries.add(feedItem);
            feed.addNewEntries(feedItem);
        }
        feedRepo.save(feed);
    }

    public FeedItem getFeedItem(Long id) {
        FeedItem feedItem = feedItemRepo.findOne(id);
        return feedItem;
    }

    public boolean isExist(TempSyndFeedStorage tempSyndFeedStorage) {
        return feedRepo.findOneByRssPath(tempSyndFeedStorage.getRssPath()) != null;
    }


    protected LocalDateTime setPubDateByDate(Date javaUtilDate) {
        Instant instant = Instant.ofEpochMilli(javaUtilDate.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    protected List<String> getAllrssLinks() {
        return feedRepo.getAllUrls();
    }

    public Feed getFeedBasedOnTempSFStorage(TempSyndFeedStorage tempSyndFeedStorage) {
        return feedRepo.findOneByRssPath(tempSyndFeedStorage.getRssPath());
    }

}
