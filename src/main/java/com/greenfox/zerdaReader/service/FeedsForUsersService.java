package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.*;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * Created by zoloe on 2017. 01. 25..
 */
@Component
public class FeedsForUsersService {
    FeedsForUsersRepository feedsForUsersRepository;

    @Autowired
    public FeedsForUsersService(FeedsForUsersRepository feedsForUsersRepository) {
        this.feedsForUsersRepository = feedsForUsersRepository;
    }

    public void populateFeedsForUsers(User user) {
        for (Feed f : user.getSubscribedFeeds()) {
            for (FeedItem fi : f.getEntries()) {
                    if (feedsForUsersRepository.findByUserAndFeedItem(user, fi) == null) {
                    user.getFeedsForUsers().add(new FeedsForUsers(user, fi));
                }
            }
        }
        feedsForUsersRepository.save(user.getFeedsForUsers());
    }

    public UserFeed getFeedsForUsersList(User user, int offset, int items) {
        Page<FeedsForUsers> allUserFeedItems;
        allUserFeedItems = feedsForUsersRepository.findAllFeedsForUsersForAuserSortedByDate(user, new PageRequest(offset, items));
        UserFeed nextFeed = new UserFeed(allUserFeedItems);
        return nextFeed;
    }

    public UserFeed getFilteredUserFeed(User user, Long feed_id, int offset, int items) {
//        Page<FeedsForUsers> allUserFeedItems;
//
//        allUserFeedItems = feedsForUsersRepository.findAllFeedItemsByUseByFeedIdSortedByDate(user, feed_id, new PageRequest(offset, items));
        Page<FeedsForUsers> allUserFeedItems = feedsForUsersRepository.findAllFeedItemsByUseByFeedIdSortedByDate(user, feed_id, new PageRequest(offset, items));
        UserFeed nextFeed = new UserFeed(allUserFeedItems);
        return nextFeed;
    }


    public void updateReadStatus(Long itemId, boolean isRead, User user) {
        FeedsForUsers feedsForUsersToUpdate = feedsForUsersRepository.findByUserAndFeedItemID(user, itemId);
        feedsForUsersToUpdate.setReadByUser(isRead);
        feedsForUsersRepository.save(feedsForUsersToUpdate);
    }

    public UserFeed getUserFeedWithFavoritesOnly(User user, int offset, int items) {
        Page<FeedsForUsers> allUserFeedItems;
        allUserFeedItems = feedsForUsersRepository.findAllFeedsForUsersForAuserSortedByDateAndByFavorites(user, new PageRequest(offset, items));
        UserFeed nextFeed = new UserFeed(allUserFeedItems);
        return nextFeed;
    }

    public void markAsFavorite(Long itemId, User user) {
        FeedsForUsers feedsForUsersToUpdate = feedsForUsersRepository.findByUserAndFeedItemID(user, itemId);
        feedsForUsersToUpdate.setStarred(true);
        feedsForUsersRepository.save(feedsForUsersToUpdate);
    }
}


