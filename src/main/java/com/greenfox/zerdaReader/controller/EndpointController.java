package com.greenfox.zerdaReader.controller;

import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.domain.UserFeed;
import com.greenfox.zerdaReader.repository.FeedItemRepository;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.UserRepository;
import com.greenfox.zerdaReader.service.EndpointService;
import com.greenfox.zerdaReader.service.FeedItemService;
import com.greenfox.zerdaReader.service.FeedService;
import com.greenfox.zerdaReader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zoloe on 2017. 01. 18..
 */

@RestController
public class EndpointController {
    private final AtomicLong counter = new AtomicLong();

    FeedItemService feedItemService;
    FeedService feedService;
    UserService userService;
    EndpointService endpointService;
    FeedRepository feedRepository;
    UserRepository userRepository;
    FeedItemRepository feedItemRepository;


    @Autowired
    public EndpointController(FeedItemRepository feedItemRepository, UserRepository userRepository, FeedItemService feedItemService, FeedService feedService, UserService userService, EndpointService endpointService, FeedRepository feedRepository) {
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
    @ResponseBody
    public List<Long> getUserIds() {
        return userRepository.getAllUserId();
    }

    //    visszaadja a DB-ből a feedidkat
    @RequestMapping(value = "/feedid")
    public List<Long> getFeedIds() {
        return feedRepository.getAllFeedId();
    }

    //    visszaadja a DB-ből a feed itfm idkat
    @RequestMapping(value = "/feeditemid")
    public List<Long> getFeedItemIds() {
        return feedItemRepository.getAllFeedItemId();
    }

    //    visszaad egy stringet Jsonban
    @RequestMapping(value = "/test")
    public String myJson() {
        return "TestJson";
    }

    //        példának marad itt
    @RequestMapping(value = "/parameterrel")
    public FeedItem feedItemJson2(@RequestParam(value = "id", required = false, defaultValue = "1") String id) {
        return feedItemService.getFeedItem(Long.parseLong(id));
    }

    //      visszaadja egy beadott user feedjét
    @RequestMapping(value = "/feed/user/{Id}")
    public UserFeed filterForFeedAndUser(@PathVariable String Id) {
//        amig nincs user auth, addig az elso usert hasznaljuk
        User user = userService.getUser(Long.parseLong(Id));
        return endpointService.getUserFeed(user);
    }

//*******************************************************
//*************** Ezek az éles endpointok ***************
//*******************************************************

    @RequestMapping(value = "/feed")
    public UserFeed allUserFeedItems() {
//         amig nincs user auth, addig az elso usert hasznaljuk
        User user = userService.getFirstUser();
        return endpointService.getUserFeed(user);
    }

    @RequestMapping(value = "/feed/{Id}")
    public UserFeed filterForFeed(@PathVariable Integer Id) {
//        amig nincs user auth, addig az elso usert hasznaljuk
        User user = userService.getFirstUser();
        return endpointService.getFilteredUserFeed(user, Id);
    }
}