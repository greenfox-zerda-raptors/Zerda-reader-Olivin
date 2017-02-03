package com.greenfox.zerdaReader.controller;

import com.greenfox.zerdaReader.domain.*;
import com.greenfox.zerdaReader.repository.FeedItemRepository;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.UserRepository;
import com.greenfox.zerdaReader.service.FeedItemService;
import com.greenfox.zerdaReader.service.FeedService;
import com.greenfox.zerdaReader.service.EndpointService;
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
    EndpointService endpointService;
    FeedRepository feedRepository;
    UserRepository userRepository;
    FeedItemRepository feedItemRepository;


    @Autowired
    public TestJsonController(FeedItemRepository feedItemRepository, UserRepository userRepository, FeedItemService feedItemService, FeedService feedService, UserService userService, EndpointService endpointService, FeedRepository feedRepository) {
        this.feedItemService = feedItemService;
        this.feedService = feedService;
        this.userService = userService;
        this.endpointService = endpointService;
        this.feedRepository = feedRepository;
        this.userRepository = userRepository;
        this.feedItemRepository = feedItemRepository;
    }
//*******************************************************
//*************** Ezek az TEST endpointok ***************
//*******************************************************

//    visszaadja a DB-ből a useridkat
    @RequestMapping(value = "/userid")
    public List<Long> getUserIds() {
        return userRepository.getAllUserId();
    }
//    visszaadja a DB-ből a feedidkat
    @RequestMapping(value = "/feedid")
    public List<Long> getFeedIds(){
        return feedRepository.getAllFeedId();
    }
//    visszaadja a DB-ből a feed itfm idkat
    @RequestMapping(value = "/feeditemid")
    public List<Long> getFeedItemIds(){
        return feedItemRepository.getAllFeedItemId();
    }
//    visszaad egy stringet Jsonban
    @RequestMapping(value = "/test")
    public TestJson myJson() {
        return new TestJson(counter.incrementAndGet(), "TestJson");
    }
    //    példának marad itt
    @RequestMapping(value = "/parameterrel")
    public FeedItem feedItemJson2(@RequestParam(value = "id", required = false, defaultValue = "1") String id) {
        return feedItemService.getFeedItem(Long.parseLong(id));
    }

//*******************************************************
//*************** Ezek az éles endpointok ***************
//*******************************************************

    @RequestMapping(value = "/feed")
    public UserFeed allUserFeedItems(){
//         amig nincs user auth, addig az elso usert hasznaljuk
        User user = userService.getFirstUser();
        return endpointService.getUserFeed(user);
    }


    @RequestMapping(value = "/feed/{Id}")
    public UserFeed filterForFeed(@PathVariable Integer Id ) {
//        amig nincs user auth, addig az elso usert hasznaljuk
        User user = userService.getFirstUser();
        return endpointService.getFilteredUserFeed(user,Id);
    }
}