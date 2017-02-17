package com.greenfox.zerdaReader.repository;

import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.FeedsForUsers;
import com.greenfox.zerdaReader.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zoloe on 2017. 01. 23..
 */
public interface FeedsForUsersRepository extends CrudRepository<com.greenfox.zerdaReader.domain.FeedsForUsers, Long> {

    FeedsForUsers findByUserAndFeedItem(User user, FeedItem feedItem);

    List<FeedsForUsers> findAll();

    @Query("select fu from FeedsForUsers fu where fu.user = ?1 order by fu.feedItem.pubDate desc ")
        // @Query(value ="SELECT * FROM feeds_for_users Inner JOIN feed_items WHERE feeds_for_users.feed_item_id = feed_items.id ORDER BY pub_date DESC", nativeQuery = true)
    Page<FeedsForUsers> findAllFeedsForUsersForAuserSortedByDate(User user, Pageable pageable);

    @Query("select fu from FeedsForUsers fu where fu.user =?1 and  fu.feedItem.feed.id =?2 order by fu.feedItem.pubDate desc ")
    Page<FeedsForUsers> findAllFeedsForUsersForAuserSortedByDateAndId(User user,Long id, Pageable pageable);


    @Query("select ffu from FeedsForUsers ffu where ffu.user = ?1 and ffu.feedItem.id = ?2")
    FeedsForUsers findByUserAndFeedItemID(User user, Long itemID);

   // @Modifying
  //  @Query("delete from FeedsForUsers fu where fu.user = ?1 and fu.feedItem.feed.id =?2")
    @Modifying
    @Query(name = "Delete FeedItem by FeedId", value = "DELETE FROM FeedsForUsers fu WHERE fu.user = :userId AND fu.feedItem.id IN (SELECT fi.feed.id FROM FeedItem fi WHERE fi.feed.id = :feedId)")
    void deleteByFeedId(@Param("userId")User user, @Param("feedId") Long feedId);


    @Query("select ffu from FeedsForUsers ffu where  ffu.feedItem.feed.id = ?1")
    List<FeedsForUsers> findAllById(Long id);
}



