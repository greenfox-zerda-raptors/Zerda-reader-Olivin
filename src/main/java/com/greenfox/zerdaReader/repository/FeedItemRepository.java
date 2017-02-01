package com.greenfox.zerdaReader.repository;

import com.greenfox.zerdaReader.domain.FeedItem;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Rita on 2017-01-18.
 */

public interface FeedItemRepository extends CrudRepository<FeedItem, Long> {
}
