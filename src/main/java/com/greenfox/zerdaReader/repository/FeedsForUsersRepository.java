package com.greenfox.zerdaReader.repository;

import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.FeedsForUsers;
import com.greenfox.zerdaReader.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zoloe on 2017. 01. 23..
 */
public interface FeedsForUsersRepository extends CrudRepository<FeedsForUsers, Long> {

    FeedsForUsers findByUserAndFeedItem(User user, FeedItem feedItem);

//    @Query("SELECT pub_date, user_id, feedItem_id, id FROM feeds_for_users JOIN feed_items ON feeds_for_users.feedItem_id = feed_items.id ORDER BY pub_date DESC", "nativeQuery = true")
//    List<FeedsForUsers> findAll(Pageable pageable);
}
