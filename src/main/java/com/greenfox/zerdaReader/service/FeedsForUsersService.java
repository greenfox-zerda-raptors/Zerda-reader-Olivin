package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.*;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

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
        List<FeedsForUsers> allUserFeedItems;
        if (offset%items == 0) {
            allUserFeedItems = feedsForUsersRepository.findAllFeedsForUsersForAUserSortedByDate(user, new PageRequest(offset/items, items));
        } else {
            List<FeedsForUsers> tempList = feedsForUsersRepository.findAllFeedsForUsersForAUserSortedByDate(user, new PageRequest(0, offset+items));
            allUserFeedItems = tempList.subList(offset-1, tempList.size()-1);
        }
        UserFeed nextFeed = new UserFeed(allUserFeedItems);
        return nextFeed;
    }

    public UserFeed getFilteredUserFeed(User user, Long feedId, int offset, int items) {
        List<FeedsForUsers> filteredUserFeedItems;
        if (offset%items == 0) {
            filteredUserFeedItems = feedsForUsersRepository.findAllFeedItemsByUserByFeedIdSortedByDate(user, feedId, new PageRequest(offset/items, items));
        } else {
            List<FeedsForUsers> tempList = feedsForUsersRepository.findAllFeedItemsByUserByFeedIdSortedByDate(user, feedId, new PageRequest(0, offset+items));
            filteredUserFeedItems = tempList.subList(offset-1, tempList.size()-1);
        }
        UserFeed nextFeed = new UserFeed(filteredUserFeedItems);
        return nextFeed;
    }


    public void updateReadStatus(Long itemId, boolean isRead, User user) {
        FeedsForUsers feedsForUsersToUpdate = feedsForUsersRepository.findByUserAndFeedItemID(user, itemId);
        feedsForUsersToUpdate.setReadByUser(isRead);
        feedsForUsersRepository.save(feedsForUsersToUpdate);
    }

    public UserFeed getUserFeedWithFavoritesOnly(User user, int offset, int items) {
        List<FeedsForUsers> favoritedUserFeedItems;
        if (offset%items == 0) {
            favoritedUserFeedItems = feedsForUsersRepository.findAllFeedsForUsersForAUserSortedByDateAndByFavorites(user, new PageRequest(offset/items, items));
        } else {
            List<FeedsForUsers> tempList = feedsForUsersRepository.findAllFeedsForUsersForAUserSortedByDateAndByFavorites(user, new PageRequest(0, offset+items));
            favoritedUserFeedItems = tempList.subList(offset-1, tempList.size()-1);
        }
        UserFeed nextFeed = new UserFeed(favoritedUserFeedItems);
        return nextFeed;
    }

    public void markAsFavorite(Long itemId, User user) {
        FeedsForUsers feedsForUsersToUpdate = feedsForUsersRepository.findByUserAndFeedItemID(user, itemId);
        feedsForUsersToUpdate.setStarred(true);
        feedsForUsersRepository.save(feedsForUsersToUpdate);
    }
}


