package com.greenfox.zerdaReader.domain;

import lombok.Data;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zoloe on 2017. 01. 25..
 */
@Data
@Log
public class UserFeed {
    private List<UserFeedItem> feed;

    public UserFeed getUserFeed(User user, int offset, int items) {
        ArrayList<UserFeedItem> allUserFeedItems = new ArrayList<>();
//        ez azért szar így, mert ha nagyra nő a feeditem egy usernek, mindig le kell kérnünk az egészet, hogy sorba tudjuk rakni idő szerint,
//        és csak utána válogatunk le belőle 50-et
        for (FeedsForUsers fi : user.getFeedsForUsers()) {
            allUserFeedItems.add(new UserFeedItem(fi));
        }
        allUserFeedItems.sort(Comparator.comparing(UserFeedItem::getCreated));
        List<UserFeedItem> subList;
//        ez azért kell, mert a sublisf OutOfBounds -ra futhat - meg kell beszélni, hogy ilyenkor mi legyen
        try {
            subList = allUserFeedItems.subList(offset,offset+items);
        }catch (Exception e){
            log.info(e.getMessage());
            subList = allUserFeedItems;
        }
        UserFeed userFeed = new UserFeed();
        userFeed.setFeed(subList);
        return userFeed;
    }

    public UserFeed getFilteredUserFeed(User user, Integer feed_id) {
        ArrayList<UserFeedItem> userFeedItems = new ArrayList<>();
        for (FeedsForUsers fa : user.getFeedsForUsers()) {
            if (feed_id == fa.getFeedItem().getFeed().getId()) {
                userFeedItems.add(new UserFeedItem(fa));
            }
        }
        UserFeed userFeed = new UserFeed();
        userFeed.setFeed(userFeedItems);
        return userFeed;
    }
}
