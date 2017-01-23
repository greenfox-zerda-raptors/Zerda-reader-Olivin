package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.repository.FeedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Rita on 2017-01-23.
 */
@Component
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
