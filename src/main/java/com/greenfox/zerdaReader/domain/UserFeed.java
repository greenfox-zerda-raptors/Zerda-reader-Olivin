package com.greenfox.zerdaReader.domain;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zoloe on 2017. 01. 25..
 */
@Data
public class UserFeed {
    private ArrayList<UserFeedItem> feed;
}
