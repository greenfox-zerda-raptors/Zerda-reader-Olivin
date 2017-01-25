package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.FeedsForUsers;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.domain.UserFeedItemsToCustomJson;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
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

    public ArrayList<UserFeedItemsToCustomJson> getThatFuckingListFinally(User user) {
        ArrayList<UserFeedItemsToCustomJson> l= new ArrayList<>();
        for (FeedsForUsers fi:user.getFeedsForUsers()
             ) {
            l.add( new UserFeedItemsToCustomJson(fi));
        }
        return l;
    }
}
