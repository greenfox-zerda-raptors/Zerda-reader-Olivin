package com.zerdareader;

import com.rometools.rome.feed.synd.SyndEntry;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public FeedService(FeedRepository feedRepo, FeedItemRepository feedItemRepo) {
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
        output.setPubDate(convertDate(storage.getSyndFeed().getPublishedDate()));
        return output;
    }

    public void addAllEntries(TempSyndFeedStorage storage) {
        Feed feed = createNewFeed(storage);
        List<SyndEntry> tempEntries = storage.getSyndFeed().getEntries();
        for (SyndEntry te : tempEntries) {
            addNewEntry(te, feed);
        }
        feedRepo.save(feed);
    }

    public void addNewEntry(SyndEntry entry, Feed feed) {
        FeedItem feedItem = new FeedItem();
        feedItem.setTitle(entry.getTitle());
        feedItem.setDescription(entry.getDescription().getValue());
        feedItem.setLink(entry.getLink());
        feedItem.setAuthor(entry.getAuthor());
        feedItem.setFeed(feed);
        feedItem.setPubDate(convertDate(entry.getPublishedDate()));
        feed.addNewEntries(feedItem);
    }

    public FeedItem getFeedItem(Long id) {
        FeedItem feedItem = feedItemRepo.findOne(id);
        return feedItem;
    }

    public boolean isExist(TempSyndFeedStorage tempSyndFeedStorage) {
        return feedRepo.findOneByRssPath(tempSyndFeedStorage.getRssPath()) != null;
    }


    protected LocalDateTime convertDate(Date javaUtilDate) {
        Instant instant = Instant.ofEpochMilli(javaUtilDate.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    protected List<String> getAllrssLinks() {
        return feedRepo.getAllUrls();
    }

    public Feed getFeedBasedOnTempSFStorage(TempSyndFeedStorage tempSyndFeedStorage) {
        return feedRepo.findOneByRssPath(tempSyndFeedStorage.getRssPath());
    }

    public Feed getFeed(Long id) {
        return feedRepo.findOne(id);
    }

    public Feed getFeed(TempSyndFeedStorage storage) {
        return feedRepo.findOneByRssPath(storage.getRssPath());
    }

    public void updateFeed(Feed feed) {
        feedRepo.save(feed);
    }
}
