package com.greenfox.zerdaReader.controller;

import com.greenfox.zerdaReader.domain.*;
import com.greenfox.zerdaReader.service.FeedItemService;
import com.greenfox.zerdaReader.service.FeedService;
import com.greenfox.zerdaReader.service.FeedsForUsersService;
import com.greenfox.zerdaReader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zoloe on 2017. 01. 18..
 */

@RestController
public class TestJsonController {
    private final AtomicLong counter = new AtomicLong();

    FeedItemService itemService;
    FeedService feedService;
    UserService userService;
    FeedsForUsersService feedsForUsersService;

    @Autowired
    public TestJsonController(FeedItemService itemService, FeedService feedService, UserService userService, FeedsForUsersService feedsForUsersService) {
        this.itemService = itemService;
        this.feedService = feedService;
        this.userService = userService;
        this.feedsForUsersService = feedsForUsersService;
    }

    @RequestMapping(value = "/list")
    public TestJson myJson() {
        return new TestJson(counter.incrementAndGet(), "valami");
    }

    @RequestMapping(value = "/j")
    public FeedItem feedItemJson() {
        return itemService.getFeedItem(1L);
    }

    @RequestMapping(value = "/parameterrel")
    public FeedItem feedItemJson2(@RequestParam(value = "id", required = false, defaultValue = "1") String id) {
        return itemService.getFeedItem(Long.parseLong(id));
    }

    @RequestMapping(value = "/{Id}")
    public FeedItem feedItemJson3(@PathVariable String Id) {
        return itemService.getFeedItem(Long.parseLong(Id));
    }

//    @RequestMapping(value = "/feed/{Id}")
//    public Feed feedItemJson4(@PathVariable String Id) {
//        return feedService.getFeed((Long.parseLong(Id)));
//    }

    @RequestMapping(value = "/User/{Id}")
    public List<FeedsForUsers> feedItemJson5(@PathVariable String Id) {
        return userService.getUser(Long.parseLong(Id)).getFeedsForUsers();
    }
    @RequestMapping(value = "/x")
    public FeedsForUsers feedItemJson6() {
        List<FeedsForUsers> al;
        al = userService.getUser(1L).getFeedsForUsers();
        return al.get(2);
    }

    @RequestMapping(value = "/x2")
    public ArrayList<UserFeedItemsToCustomJson> feedItemJso7() {
        ArrayList<UserFeedItemsToCustomJson> feed = feedsForUsersService.getFeedItemsForUser(userService.getUser(1L));
        return feed;
    }

    @RequestMapping(value = "/x3")
    public UserFeed feedItemJso8() {
        UserFeed userFeed = new UserFeed();
        userFeed.setFeed(feedsForUsersService.getFeedItemsForUser(userService.getUser(1L)));
        return userFeed;
    }

    @RequestMapping(value = "/feed/{Id}")
    public UserFeed filterForFeed(@PathVariable Integer Id ) {
        UserFeed userFeed = new UserFeed();
        User user = userService.getUser(1L);
        ArrayList<UserFeedItemsToCustomJson> x = feedsForUsersService.getFeedForUser(user,Id);
        userFeed.setFeed(x);
        return userFeed;
    }
}