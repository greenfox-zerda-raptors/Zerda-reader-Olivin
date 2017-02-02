package com.greenfox.zerdaReader.repository;

import com.greenfox.zerdaReader.domain.FeedItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Rita on 2017-01-18.
 */

public interface FeedItemRepository extends CrudRepository<FeedItem, Long> {

    @Query("select f.id from FeedItem f")
    List<Long> getAllFeedItemId();
}
