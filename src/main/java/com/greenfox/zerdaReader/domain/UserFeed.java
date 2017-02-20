package com.greenfox.zerdaReader.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoloe on 2017. 01. 25..
 */
@Setter
@NoArgsConstructor
@Getter
@Log
public class UserFeed {

    private List<UserFeedItem> feed;

    public UserFeed(List<FeedsForUsers> rawFeed) {
        this.feed = new ArrayList<>();
        for (FeedsForUsers fi : rawFeed) {
            feed.add(new UserFeedItem(fi));
        }
    }
}
