package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.FeedItemsForUsersRepository;
import com.greenfox.zerdaReader.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by zoloe on 2017. 02. 16..
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ZerdaReaderApplication.class)
@WebAppConfiguration
@DataJpaTest
@EnableWebMvc
public class SubscriptionServiceTest {

    @Autowired
    private SubscriptionService subscriptionService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedItemsForUsersRepository feedItemsForUsersRepository;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .addFilters(filterChainProxy)
                .build();
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testSubscribeToExistingFeed() throws Exception {
        User testUser = userRepository.findOne(3L);
        Assert.assertEquals(0, testUser.getSubscribedFeeds().size());
        Assert.assertEquals(0, testUser.getFeedItemsForUsers().size());
        subscriptionService.trySubscribingToFeedAndReturn("http://lorem-rss.herokuapp.com/feed?unit=second&interval=30", testUser);
        Assert.assertEquals(1, testUser.getSubscribedFeeds().size());
        Assert.assertEquals(12, feedItemsForUsersRepository.count());
        Assert.assertEquals(10, feedItemsForUsersRepository.findAllFeedsForUsersForAuserSortedByDate(testUser, new PageRequest(0, 50)).size());
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testSubscribeToBrandnewFeed(){
        User testUser = userRepository.findOne(3L);
        Assert.assertEquals(0, testUser.getSubscribedFeeds().size());
        Assert.assertEquals(0, testUser.getFeedItemsForUsers().size());
        subscriptionService.trySubscribingToFeedAndReturn("http://lorem-rss.herokuapp.com/feed?unit=second&interval=60", testUser);
        Assert.assertEquals(1, testUser.getSubscribedFeeds().size());
        Assert.assertEquals(12, feedItemsForUsersRepository.count());
        Assert.assertEquals(10, feedItemsForUsersRepository.findAllFeedsForUsersForAuserSortedByDate(testUser, new PageRequest(0, 50)).size());
    }

    @Test
    public void testSubscribeToNonFeedUrl(){
        User testUser = userRepository.findOne(3L);
        Assert.assertEquals("{\"result\": \"fail\",\"message\": \"The URL provided is not valid.\"}",subscriptionService.trySubscribingToFeedAndReturn("hsjhdkjs", testUser));
    }
}
