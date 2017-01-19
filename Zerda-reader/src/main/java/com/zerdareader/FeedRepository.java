package com.zerdareader;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Rita on 2017-01-18.
 */
public interface FeedRepository extends CrudRepository<Feed, Long> {

    @Query("select f.rssPath from Feed f")
    List<String> getAllUrls();
}
