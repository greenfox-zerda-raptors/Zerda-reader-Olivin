package com.greenfox.zerdaReader.repository;

import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.FeedsForUsers;
import com.greenfox.zerdaReader.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zoloe on 2017. 01. 23..
 */
public interface FeedsForUsersRepository extends CrudRepository<com.greenfox.zerdaReader.domain.FeedsForUsers, Long> {

    FeedsForUsers findByUserAndFeedItem(User user, FeedItem feedItem);

    @Query("select ffu from FeedsForUsers ffu where ffu.user = ?1 and ffu.feedItem.id=?2")
    FeedsForUsers findByUserAndFeedItemID(User user, Long itemID);

    @Query("select fu from FeedsForUsers fu where fu.user = ?1 order by fu.feedItem.pubDate desc ")
    List<FeedsForUsers> findAllFeedsForUsersForAUserSortedByDate(User user, Pageable pageable);

    @Query(value = "select fu from FeedsForUsers fu where fu.user = ?1 and fu.feedItem.feed.id = ?2 order by fu.feedItem.pubDate desc ")
    List<FeedsForUsers> findAllFeedItemsByUserByFeedIdSortedByDate(User user, Long Id, Pageable pageable);

    @Query("select fu from FeedsForUsers fu where fu.user = ?1 and fu.starred = TRUE order by fu.feedItem.pubDate desc")
    List<FeedsForUsers> findAllFeedsForUsersForAUserSortedByDateAndByFavorites(User user, Pageable pageable);
}

