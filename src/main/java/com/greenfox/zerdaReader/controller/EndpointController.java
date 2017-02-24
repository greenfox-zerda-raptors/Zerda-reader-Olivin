package com.greenfox.zerdaReader.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.greenfox.zerdaReader.domain.FeedItem;
import com.greenfox.zerdaReader.domain.Subscriptions;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.domain.UserFeed;
import com.greenfox.zerdaReader.repository.FeedItemRepository;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.UserRepository;
import com.greenfox.zerdaReader.service.FeedItemService;
import com.greenfox.zerdaReader.service.FeedItemsForUsersService;
import com.greenfox.zerdaReader.service.SubscriptionService;
import com.greenfox.zerdaReader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by zoloe on 2017. 01. 18..
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class EndpointController {

    FeedItemService feedItemService;
    UserService userService;
    FeedRepository feedRepository;
    UserRepository userRepository;
    FeedItemRepository feedItemRepository;
    FeedItemsForUsersService feedItemsForUsersService;
    SubscriptionService subscriptionService;


    @Autowired
    public EndpointController(FeedItemRepository feedItemRepository,
                              UserRepository userRepository,
                              FeedItemService feedItemService,
                              UserService userService,
                              FeedRepository feedRepository,
                              FeedItemsForUsersService feedsForUsersService,
                              SubscriptionService subscriptionService) {

        this.feedItemService = feedItemService;
        this.userService = userService;
        this.feedRepository = feedRepository;
        this.userRepository = userRepository;
        this.feedItemRepository = feedItemRepository;
        this.feedItemsForUsersService = feedsForUsersService;
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

//*******************************************************
//*************** Ezek az éles endpointok ***************
//*******************************************************

    @RequestMapping(value = "/feed")
    public UserFeed allUserFeedItems(@RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
                                     @RequestParam(value = "items", required = false, defaultValue = "50") String items) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserFeed myUserFeed = feedItemsForUsersService.getFeedsForUsersList(user,Integer.parseInt(offset),Integer.parseInt(items));
        return myUserFeed;
    }

    @RequestMapping(value = "/feed/{Id}")
    public UserFeed filterForFeed(@PathVariable Long Id,
                                  @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
                                  @RequestParam(value = "items", required = false, defaultValue = "50") String items) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return feedItemsForUsersService.getFilteredUserFeed(user, Id, Integer.parseInt(offset), Integer.parseInt(items));
    }

    @RequestMapping(value = "/feed/{itemId}", method = RequestMethod.PUT)
    public HttpStatus markAsRead(@PathVariable Long itemId,
                                 @RequestBody String openedStatus) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode request = mapper.readTree(openedStatus);
        boolean isRead = request.get("opened").asBoolean();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        feedItemsForUsersService.updateReadStatus(itemId, isRead, user);

        return HttpStatus.OK;
    }

    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
    public Subscriptions getSubscriptions() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Subscriptions(user.getSubscribedFeeds());
    }

    @RequestMapping(value = "/favorites", method = RequestMethod.GET)
    public UserFeed listFavoriteFeedItems(@RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
                                          @RequestParam(value = "items", required = false, defaultValue = "50") String items) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return feedItemsForUsersService.getUserFeedWithFavoritesOnly(user, Integer.parseInt(offset), Integer.parseInt(items));
    }

    @RequestMapping(value = "/favorites", method = RequestMethod.POST)
    public ObjectNode markAsFavorite(@RequestBody String itemIdOfItemToChange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode request = mapper.readTree(itemIdOfItemToChange);
        ObjectNode response = mapper.createObjectNode();
        long itemId = request.get("item_id").asLong();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            feedItemsForUsersService.markAsFavorite(itemId, user);
            response.put("response", "success");
        } catch (NullPointerException e) {
            response.put("response", "invalid item id");
        }
        return response;
    }

    @RequestMapping(value = "/favorites", method = RequestMethod.DELETE)
    public ObjectNode removeFavorite (@RequestBody String itemIdOfItemToChange) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode request = mapper.readTree(itemIdOfItemToChange);
        ObjectNode response = mapper.createObjectNode();
        long itemId = request.get("item_id").asLong();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            feedItemsForUsersService.removeFavorite(itemId, user);
            response.put("response", "success");
        } catch (NullPointerException e) {
            response.put("response", "error message");
        }
        return response;
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity<JsonNode> subscribeToFeed(@RequestBody String subscriptionRequest) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode request = mapper.readTree(subscriptionRequest);
        String url = (request.get("feed").asText());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JsonNode answer = mapper.readTree(subscriptionService.trySubscribingToFeedAndReturn(url, user));
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/subscribe/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonNode> unSubscribe( @PathVariable User user,
                                                 @PathVariable Long id,
                                                 @RequestParam(value = "token") String token) throws IOException {
        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode answer;
        feedItemsForUsersService.deleteFeedItemsForUser(user,id);
        answer = mapper.readTree(feedItemsForUsersService.generateResponseForDeletion(true));
        return new ResponseEntity<JsonNode>(answer, HttpStatus.OK);
    }
}
