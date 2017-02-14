package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.Feed;
import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.FeedsForUsers;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public FeedsForUsersService() {

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

//    public Page<FeedsForUsers> getFeedsForUserPage (int offset, int items) {
//       return feedsForUsersRepository.findAll(new PageRequest(offset,items));
//    }

}
