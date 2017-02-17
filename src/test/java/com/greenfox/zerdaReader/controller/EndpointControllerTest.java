package com.greenfox.zerdaReader.controller;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.domain.Feed;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.FeedRepository;
import com.greenfox.zerdaReader.repository.UserRepository;
import com.greenfox.zerdaReader.service.FeedsForUsersService;
import com.greenfox.zerdaReader.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;



/**
 * Created by zoloe on 2017. 02. 03..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ZerdaReaderApplication.class)
@WebAppConfiguration
@DataJpaTest
@EnableWebMvc
public class EndpointControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FeedsForUsersService feedsForUsersService;

    @Autowired
    FilterChainProxy filterChainProxy;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .addFilters(filterChainProxy)
                .build();
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testExample() throws Exception {
        mockMvc.perform(get("/userid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0]", is(2)));
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTablesForUserFeedEndpointTests.sql"})
    public void testUserFeedPaginationByDefalutParams() throws Exception {
        mockMvc.perform(get("/feed?token=QWERTY9876"))
//                http://reader-api.example/feed/12521?offset=25&items=50
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
//                      check if the number of feeditems are 50
                .andExpect(jsonPath("$.feed.*", hasSize(50)))
//                      check if the 1st feeditem is the 1st in the db
                .andExpect(jsonPath("$.feed[0].id", is(120)));
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTablesForUserFeedEndpointTests.sql"})
    public void testUserFeedPaginationByOffset25() throws Exception {
        mockMvc.perform(get("/feed?offset=2&items=20&token=QWERTY9876"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.feed.*", hasSize(20)))
               .andExpect(jsonPath("$.feed[0].id", is(80)));

    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestSuccessfulMarkAsRead() throws Exception {
        User newUser = userService.addNewUser("example@gmail.com", "12345");
        Feed feed = feedRepository.findOne(2L);
        newUser.getSubscribedFeeds().add(feed);
        newUser = userRepository.save(newUser);
        feedsForUsersService.populateFeedsForUsers(newUser);
        userRepository.save(newUser);
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"example@gmail.com\", \"password\": \"12345\"}"));
        String token = newUser.getToken();
        mockMvc.perform(put("/feed/11?token=" + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"opened\": 1}"))
                .andExpect(status().isOk());

    }
    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestEndpointBasicFuctions() throws Exception {
        User user;
        user = userRepository.findOne(2L);
        feedsForUsersService.deleteFeedFromRepo(user, 3L);
        user.getSubscribedFeeds().remove(3L);
        mockMvc.perform(delete("/subscribe/3?token=ABCD1234")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"result\": \"success\"}"))
                .andExpect(status().isOk());

    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestFeedItemisDeletedFromFFU() throws Exception{
        User user;
        user = userRepository.findOne(2L);
        feedsForUsersService.deleteFeedFromRepo(user, 3L);
        user.getSubscribedFeeds().remove(3L);
        userRepository.save(user);
        Assert.assertEquals(1,user.getSubscribedFeeds().size());
        Assert.assertEquals(1,feedsForUsersService.allllFeedsForUsers().size());
    }


}






