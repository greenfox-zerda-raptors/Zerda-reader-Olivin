package com.greenfox.zerdaReader.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.domain.UserFeed;
import com.greenfox.zerdaReader.repository.FeedItemRepository;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.UserRepository;
import com.greenfox.zerdaReader.service.FeedItemService;
import com.greenfox.zerdaReader.service.FeedsForUsersService;
import com.greenfox.zerdaReader.service.SubscriptionService;
import com.greenfox.zerdaReader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zoloe on 2017. 01. 18..
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class EndpointController {
    private final AtomicLong counter = new AtomicLong();

    FeedItemService feedItemService;
    UserService userService;
    FeedRepository feedRepository;
    UserRepository userRepository;
    FeedItemRepository feedItemRepository;
    FeedsForUsersService feedsForUsersService;
    SubscriptionService subscriptionService;


    @Autowired
    public EndpointController(FeedItemRepository feedItemRepository,
                              UserRepository userRepository,
                              FeedItemService feedItemService,
                              UserService userService,
                              FeedRepository feedRepository,
                              FeedsForUsersService feedsForUsersService,
                              SubscriptionService subscriptionService) {

        this.feedItemService = feedItemService;
        this.userService = userService;
        this.feedRepository = feedRepository;
        this.userRepository = userRepository;
        this.feedItemRepository = feedItemRepository;
        this.feedsForUsersService = feedsForUsersService;
        this.subscriptionService = subscriptionService;
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

    //    Todo: ezt szedjük azért ki innen
    //    visszaadja a DB-ből a usereket
    @RequestMapping(value = "/users")
    @ResponseBody
    public List<String> getUserTokens() {
        return userRepository.getAllUserTokens();
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
        User user = userService.getUserById(Long.parseLong(Id));
        return new UserFeed().getUserFeed(user, 0, 100);
    }

//*******************************************************
//*************** Ezek az éles endpointok ***************
//*******************************************************

    @RequestMapping(value = "/feed")
    public UserFeed allUserFeedItems(@RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
                                     @RequestParam(value = "items", required = false, defaultValue = "50") String items,
                                     @RequestParam(value = "token") String token) {
//         amig nincs user auth, addig az elso usert hasznaljuk
        User user = userService.getFirstUser();
        return new UserFeed().getUserFeed(user, Integer.parseInt(offset), Integer.parseInt(items));
    }

    @RequestMapping(value = "/feed/{Id}", method = RequestMethod.GET)
    public UserFeed filterForFeed(@PathVariable Integer Id) {
//        amig nincs user auth, addig az elso usert hasznaljuk
        User user = userService.getFirstUser();
        return new UserFeed().getFilteredUserFeed(user, Id);
    }

    @RequestMapping(value = "/feed/{itemId}", method = RequestMethod.PUT)
    public HttpStatus markAsRead(@PathVariable Long itemId,
//                                 itt a token miért kell itt?
                                 @RequestParam(value = "token") String token,
                                 @RequestBody String openedStatus) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode request = mapper.readTree(openedStatus);
        boolean isRead = request.get("opened").asBoolean();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        feedsForUsersService.updateReadStatus(itemId, isRead, user);

        return HttpStatus.OK;
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity<JsonNode> subscribeToFeed(@RequestParam(value = "token") String token, @RequestBody String subscriptionRequest) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode request = mapper.readTree(subscriptionRequest);
        String url = (request.get("feed").asText());
        JsonNode answer = mapper.readTree(subscriptionService.trySubscribingToFeedAndReturn(url));
        return new ResponseEntity<JsonNode>(answer, HttpStatus.OK);
    }

}
