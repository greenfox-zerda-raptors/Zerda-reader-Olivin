package com.greenfox.zerdaReader.repository;

import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.FeedItemsForUsers;
import com.greenfox.zerdaReader.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zoloe on 2017. 01. 23..
 */
public interface FeedItemsForUsersRepository extends CrudRepository<FeedItemsForUsers, Long> {

    FeedItemsForUsers findByUserAndFeedItem(User user, FeedItem feedItem);

    @Query("select ffu from FeedItemsForUsers ffu where ffu.user = ?1 and ffu.feedItem.id=?2")
    FeedItemsForUsers findByUserAndFeedItemID(User user, Long itemID);

   @Query("select fu from FeedItemsForUsers fu where fu.user = ?1 order by fu.feedItem.pubDate desc ")
   List<FeedItemsForUsers> findAllFeedsForUsersForAuserSortedByDate(User user, Pageable pageable);

    @Query(value="select fu from FeedItemsForUsers fu where fu.user = ?1 and fu.feedItem.feed.id = ?2 order by fu.feedItem.pubDate desc ")
    List<FeedItemsForUsers> findAllFeedItemsByUseByFeedIdSortedByDate (User user, Long Id, Pageable pageable);

    @Query("select fu from FeedItemsForUsers fu where fu.user = ?1 and fu.starred = TRUE order by fu.feedItem.pubDate desc")
    List<FeedItemsForUsers>findAllFeedsForUsersForAuserSortedByDateAndByFavorites(User user, Pageable pageable);

    @Query("select fu from FeedItemsForUsers fu where fu.user =?1 and  fu.feedItem.feed.id =?2")
    List<FeedItemsForUsers> findAllById(User user,Long id);

    @Query("select fu from FeedItemsForUsers fu where fu.user =?1 and  fu.feedItem.feed.id =?2")
    FeedItemsForUsers findOneById(User user,Long id);

    @Query("delete from FeedItemsForUsers fu where  fu.id =?1")
    List<FeedItemsForUsers> deleteById(Long id);

    List<FeedItemsForUsers> findAll();
}

