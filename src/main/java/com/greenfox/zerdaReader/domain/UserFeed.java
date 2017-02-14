package com.greenfox.zerdaReader.domain;

import com.greenfox.zerdaReader.repository.FeedsForUsersRepository;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by zoloe on 2017. 01. 25..
 */
@Data
@Log
public class UserFeed {

    private List<UserFeedItem> feed;

    @Autowired
    FeedsForUsersRepository feedsForUsersRepository;

    public UserFeed getUserFeed(User user, int offset, int items) {
        List<UserFeedItem> allUserFeedItems2 = new ArrayList<>();
//        ez azért szar így, mert ha nagyra nő a feeditem egy usernek, mindig le kell kérnünk az egészet, hogy sorba tudjuk rakni idő szerint,
//        és csak utána válogatunk le belőle 50-et
        Page<FeedsForUsers> allUserFeedItems = feedsForUsersRepository.findAllFeedsForUsersForAuserSortedByDate(user, new PageRequest(offset,items));
        for (FeedsForUsers fi : allUserFeedItems) {
            allUserFeedItems2.add(new UserFeedItem(fi));
        }
//        allUserFeedItems.sort(Comparator.comparing(UserFeedItem::getCreated));
//        List<UserFeedItem> subList;
//        ez azért kell, mert a sublisf OutOfBounds -ra futhat - meg kell beszélni, hogy ilyenkor mi legyen
//        try {
//            subList = allUserFeedItems.subList(offset,offset+items);
//        }catch (Exception e){
//            log.info(e.getMessage());
//            subList = allUserFeedItems;
//        }
        UserFeed userFeed = new UserFeed();
//        userFeed.setFeed(allUserFeedItems2);
        feed = allUserFeedItems2;
//        return userFeed;
        return userFeed;
    }

//    public UserFeed getFilteredUserFeed(User user, Integer feed_id) {
//        ArrayList<UserFeedItem> userFeedItems = new ArrayList<>();
//        for (FeedsForUsers fa : user.getFeedsForUsers()) {
//            if (feed_id == fa.getFeedItem().getFeed().getId()) {
//                userFeedItems.add(new UserFeedItem(fa));
//            }
//        }
//        UserFeed userFeed = new UserFeed();
//        userFeed.setFeed(userFeedItems);
//        return userFeed;
//    }
}
