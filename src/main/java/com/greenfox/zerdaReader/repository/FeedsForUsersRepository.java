package com.greenfox.zerdaReader.repository;

import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.FeedsForUsers;
import com.greenfox.zerdaReader.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zoloe on 2017. 01. 23..
 */
public interface FeedsForUsersRepository extends CrudRepository<com.greenfox.zerdaReader.domain.FeedsForUsers, Long> {

    FeedsForUsers findByUserAndFeedItem(User user, FeedItem feedItem);

    @Query("select ffu from FeedsForUsers ffu where ffu.user = ?1 and ffu.feedItem.id = ?2")
    FeedsForUsers findByUserAndFeedItemID(User user, Long itemID);
}
