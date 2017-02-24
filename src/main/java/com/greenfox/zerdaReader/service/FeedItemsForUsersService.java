package com.greenfox.zerdaReader.service;
import com.greenfox.zerdaReader.domain.*;
import com.greenfox.zerdaReader.repository.FeedItemsForUsersRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Created by zoloe on 2017. 01. 25..
 */


@Component
@Log
public class FeedItemsForUsersService {
    FeedItemsForUsersRepository feedItemsForUsersRepository;

    @Autowired
    public FeedItemsForUsersService(FeedItemsForUsersRepository feedItemsForUsersRepository) {
        this.feedItemsForUsersRepository = feedItemsForUsersRepository;
    }

    public void populateFeedItemsForUser(User user) {
        for (Feed f : user.getSubscribedFeeds()) {
            for (FeedItem fi : f.getEntries()) {
                    if (feedItemsForUsersRepository.findByUserAndFeedItem(user, fi) == null) {
                    user.getFeedItemsForUsers().add(new FeedItemsForUsers(user, fi));
                }
            }
        }
        feedItemsForUsersRepository.save(user.getFeedItemsForUsers());
    }

    public void populateFeedItemsForOneFeedAndUser(User user, Feed feed) {
            for (FeedItem fi : feed.getEntries()) {
                    if (feedItemsForUsersRepository.findByUserAndFeedItem(user, fi) == null) {
                    user.getFeedItemsForUsers().add(new FeedItemsForUsers(user, fi));
                }
            }
        feedItemsForUsersRepository.save(user.getFeedItemsForUsers());
    }


    public UserFeed getFeedsForUsersList(User user, int offset, int items) {
        log.info("break3 / in feedsforusers service, getfeedsforuserslist");
        List<FeedItemsForUsers> allUserFeedItems;
        if (offset % items == 0) {
            allUserFeedItems = feedItemsForUsersRepository.findAllFeedsForUsersForAuserSortedByDate(user, new PageRequest(offset / items, items));
        } else {
            List<FeedItemsForUsers> tempList = feedItemsForUsersRepository.findAllFeedsForUsersForAuserSortedByDate(user, new PageRequest(0, offset + items));
            allUserFeedItems = tempList.subList(offset, tempList.size());
        }
        UserFeed nextFeed = new UserFeed(allUserFeedItems);
        log.info("break4 / got list, ready to return to controller");
        return nextFeed;
    }

    public UserFeed getFilteredUserFeed(User user, Long feedId, int offset, int items) {
        List<FeedItemsForUsers> filteredUserFeedItems;
        if (offset % items == 0) {
            filteredUserFeedItems = feedItemsForUsersRepository.findAllFeedItemsByUseByFeedIdSortedByDate(user, feedId, new PageRequest(offset / items, items));
        } else {
            List<FeedItemsForUsers> tempList = feedItemsForUsersRepository.findAllFeedItemsByUseByFeedIdSortedByDate(user, feedId, new PageRequest(0, offset + items));
            filteredUserFeedItems = tempList.subList(offset, tempList.size());
        }
        UserFeed nextFeed = new UserFeed(filteredUserFeedItems);
        return nextFeed;
    }


    public void updateReadStatus(Long itemId, boolean isRead, User user) {
        FeedItemsForUsers feedItemsForUsersToUpdate = feedItemsForUsersRepository.findByUserAndFeedItemID(user, itemId);
        feedItemsForUsersToUpdate.setReadByUser(isRead);
        feedItemsForUsersRepository.save(feedItemsForUsersToUpdate);
    }

    public UserFeed getUserFeedWithFavoritesOnly(User user, int offset, int items) {
        List<FeedItemsForUsers> favoritedUserFeedItems;
        if (offset % items == 0) {
            favoritedUserFeedItems = feedItemsForUsersRepository.findAllFeedsForUsersForAuserSortedByDateAndByFavorites(user, new PageRequest(offset / items, items));
        } else {
            List<FeedItemsForUsers> tempList = feedItemsForUsersRepository.findAllFeedsForUsersForAuserSortedByDateAndByFavorites(user, new PageRequest(0, offset + items));
            favoritedUserFeedItems = tempList.subList(offset, tempList.size());
        }
        UserFeed nextFeed = new UserFeed(favoritedUserFeedItems);
        return nextFeed;
    }

    public void markAsFavorite(Long itemId, User user) {
        FeedItemsForUsers feedItemsForUsersToUpdate = feedItemsForUsersRepository.findByUserAndFeedItemID(user, itemId);
        feedItemsForUsersToUpdate.setStarred(true);
        feedItemsForUsersRepository.save(feedItemsForUsersToUpdate);
    }

    public void removeFavorite(Long itemId, User user) {
          FeedItemsForUsers feedItemsForUsersToUpdate = feedItemsForUsersRepository.findByUserAndFeedItemID(user, itemId);
          feedItemsForUsersToUpdate.setStarred(false);
          feedItemsForUsersRepository.save(feedItemsForUsersToUpdate);
    }

    public String generateResponseForDeletion(boolean b) {
        if (b){
        return "{\"result\": \"success\"}";}
        else return "{\"result\": \"no success\"}";
    }

    public List<FeedItemsForUsers> getFeedItemsForUsers(){
        return  feedItemsForUsersRepository.findAll();
    }

    public void deleteFeedItemsForUser(User user, Long id){
        List<FeedItemsForUsers> list;
           list=feedItemsForUsersRepository.findAllById(user,id);
              // for (FeedItemsForUsers fu: list) {
                   feedItemsForUsersRepository.delete(list);
        feedItemsForUsersRepository.save(user.getFeedItemsForUsers());
       }
}



