package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
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

    private SubscriptionService subscriptionService;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .addFilters(filterChainProxy)
                .build();
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testSubscribeToExistingFeed(){
        //at kell irni hogy usert is kivulrol kelljen neki adni

//        user = QWERTY9876 (id = 2)
//        feedurl = http://lorem-rss.herokuapp.com/feed?unit=second&interval=30
        subscriptionService.trySubscribingToFeedAndReturn("klstd");
//     assert feeditems

    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testSubscribeToBrandnewFeed(){
// feedurl = http://lorem-rss.herokuapp.com/feed
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void testSubscribeToNonFeedUrl(){
        // feedurl = "blah"

    }


}
