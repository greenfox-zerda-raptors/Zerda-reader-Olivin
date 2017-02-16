package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.*;
import com.greenfox.zerdaReader.repository.FeedItemsForUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * Created by zoloe on 2017. 01. 25..
 */
@Component
public class FeedItemsForUsersService {
    FeedItemsForUsersRepository feedItemsForUsersRepository;

    @Autowired
    public FeedItemsForUsersService(FeedItemsForUsersRepository feedItemsForUsersRepository) {
        this.feedItemsForUsersRepository = feedItemsForUsersRepository;
    }

    public void populateFeedsForUsers(User user) {
        for (Feed f : user.getSubscribedFeeds()) {
            for (FeedItem fi : f.getEntries()) {
                    if (feedItemsForUsersRepository.findByUserAndFeedItem(user, fi) == null) {
                    user.getFeedItemsForUsers().add(new FeedItemsForUsers(user, fi));
                }
            }
        }
        feedItemsForUsersRepository.save(user.getFeedItemsForUsers());
    }

    public UserFeed getFeedsForUsersList(User user, int offset, int items) {
        Page<FeedItemsForUsers> allUserFeedItems;
        allUserFeedItems = feedItemsForUsersRepository.findAllFeedsForUsersForAuserSortedByDate(user, new PageRequest(offset, items));
        UserFeed nextFeed = new UserFeed(allUserFeedItems);
        return nextFeed;
    }

    public UserFeed getFilteredUserFeed(User user, Long feed_id, int offset, int items) {
//        Page<FeedItemsForUsers> allUserFeedItems;
//
//        allUserFeedItems = feedsForUsersRepository.findAllFeedItemsByUseByFeedIdSortedByDate(user, feed_id, new PageRequest(offset, items));
        Page<FeedItemsForUsers> allUserFeedItems = feedItemsForUsersRepository.findAllFeedItemsByUseByFeedIdSortedByDate(user, feed_id, new PageRequest(offset, items));
        UserFeed nextFeed = new UserFeed(allUserFeedItems);
        return nextFeed;
    }


    public void updateReadStatus(Long itemId, boolean isRead, User user) {
        FeedItemsForUsers feedItemsForUsersToUpdate = feedItemsForUsersRepository.findByUserAndFeedItemID(user, itemId);
        feedItemsForUsersToUpdate.setReadByUser(isRead);
        feedItemsForUsersRepository.save(feedItemsForUsersToUpdate);
    }

    public UserFeed getUserFeedWithFavoritesOnly(User user, int offset, int items) {
        Page<FeedItemsForUsers> allUserFeedItems;
        allUserFeedItems = feedItemsForUsersRepository.findAllFeedsForUsersForAuserSortedByDateAndByFavorites(user, new PageRequest(offset, items));
        UserFeed nextFeed = new UserFeed(allUserFeedItems);
        return nextFeed;
    }

    public void markAsFavorite(Long itemId, User user) {
        FeedItemsForUsers feedItemsForUsersToUpdate = feedItemsForUsersRepository.findByUserAndFeedItemID(user, itemId);
        feedItemsForUsersToUpdate.setStarred(true);
        feedItemsForUsersRepository.save(feedItemsForUsersToUpdate);
    }
}


