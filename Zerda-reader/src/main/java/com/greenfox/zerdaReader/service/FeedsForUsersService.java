package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.FeedsForUsers;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

//    public String getStarredForFeedItemAndUser(FeedItem feedItem, User user){
//        String hql = "starred from feedsForUsers where use_id=:userid and feedItem_id=:FeedItemId";
//        Query query = session.createQuery(hql);
//        query.setParameter("userid",user.getId());
//        query.setParameter("FeedItemId",feedItem.getId());
//        return query.list();
//    }
}
//
//    @JoinColumn(name = "user_id")
//    private User user;
//@ManyToOne
//@JoinColumn(name = "feedItem_id")