package com.greenfox.zerdaReader.repository;

import com.greenfox.zerdaReader.domain.Feed;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Rita on 2017-01-18.
 */
public interface FeedRepository extends CrudRepository<Feed, Long> {

    Feed findOneByRssPath(String rssPath);
}
