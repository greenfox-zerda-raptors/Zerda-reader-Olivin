package com.greenfox.zerdaReader.controller;

import com.greenfox.zerdaReader.domain.*;
import com.greenfox.zerdaReader.repository.FeedItemRepository;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.UserRepository;
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

    FeedItemService feedItemService;
    FeedService feedService;
    UserService userService;
    FeedsForUsersService feedsForUsersService;
    FeedRepository feedRepository;
    UserRepository userRepository;
    FeedItemRepository feedItemRepository;

    @Autowired
    public TestJsonController(FeedItemRepository feedItemRepository, UserRepository userRepository, FeedItemService feedItemService, FeedService feedService, UserService userService, FeedsForUsersService feedsForUsersService, FeedRepository feedRepository) {
        this.feedItemService = feedItemService;
        this.feedService = feedService;
        this.userService = userService;
        this.feedsForUsersService = feedsForUsersService;
        this.feedRepository = feedRepository;
        this.userRepository = userRepository;
        this.feedItemRepository = feedItemRepository;
    }

    @RequestMapping(value = "/userid")
    public List<Long> getUserIds() {
        return userRepository.getAllUserId();
    }

    @RequestMapping(value = "/feedid")
    public List<Long> getFeedIds(){
        return feedRepository.getAllFeedId();
    }

    @RequestMapping(value = "/feeditemid")
    public List<Long> getFeedItemIds(){
        return feedItemRepository.getAllFeedItemId();
    }

    @RequestMapping(value = "/list")
    public TestJson myJson() {
        return new TestJson(counter.incrementAndGet(), "valami");
    }

    @RequestMapping(value = "/j")
    public FeedItem feedItemJson() {
        Long id = feedRepository.getAllFeedId().get(0);
        return feedItemService.getFeedItem(id);
    }

    @RequestMapping(value = "/parameterrel")
    public FeedItem feedItemJson2(@RequestParam(value = "id", required = false, defaultValue = "1") String id) {
        return feedItemService.getFeedItem(Long.parseLong(id));
    }

    @RequestMapping(value = "/{Id}")
    public FeedItem feedItemJson3(@PathVariable String Id) {
        return feedItemService.getFeedItem(Long.parseLong(Id));
    }

    @RequestMapping(value = "/feed/{Id}")
    public Feed feedItemJson4(@PathVariable String Id) {
        return feedService.getFeed((Long.parseLong(Id)));
    }

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

    @RequestMapping(value = "/userfeed/{Id}")
    public UserFeed feedItemJso8(@PathVariable String Id) {
        UserFeed userFeed = new UserFeed();
        userFeed.setFeed(feedsForUsersService.getFeedItemsForUser(userService.getUser(Long.parseLong(Id))));
        return userFeed;
    }
}