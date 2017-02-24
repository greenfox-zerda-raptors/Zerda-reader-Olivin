package com.greenfox.zerdaReader.repository;

import com.greenfox.zerdaReader.domain.Feed;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Rita on 2017-01-18.
 */
public interface FeedRepository extends CrudRepository<Feed, Long> {

    Feed findOneByRssPath(String rssPath);

    @Query("select f.id from Feed f")
    List<Long> getAllFeedId();
//
//    //    @Query("select f from ")
//    List<Feed> findAllBySubscribedUser(User user);

}

