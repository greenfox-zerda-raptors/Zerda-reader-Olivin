package com.zerdareader;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Rita on 2017-01-23.
 */
public class FeedItemService {
    FeedItemRepository repo;

    @Autowired
    public FeedItemService(FeedItemRepository repo) {
        this.repo = repo;
    }

    public FeedItem getFeedItem(Long id) {
        return repo.findOne(id);
    }
}
