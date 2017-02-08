package com.greenfox.zerdaReader.controller;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * Created by zoloe on 2017. 02. 03..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZerdaReaderApplication.class)
@WebAppConfiguration
@DataJpaTest
@EnableWebMvc
@Log
public class EndpointControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
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
        MvcResult mvcResult = mockMvc.perform(get("/feed"))
//                http://reader-api.example/feed/12521?offset=25&items=50
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
//                      check if the number of feeditems are 50
                .andExpect(jsonPath("$.feed.*", hasSize(50)))
//                      check if the 1st feeditem is the 1st in the db
                .andExpect(jsonPath("$.feed[0].id", is(1)))
                .andReturn();
        String resultcontent = mvcResult.getResponse().getContentAsString();
  log.info(resultcontent);
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTablesForUserFeedEndpointTests.sql"})
    public void testUserFeedPaginationByOffset25() throws Exception {
         mockMvc.perform(get("/feed?offset=25&items=20"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
//         check if the number of feeditems are 20
                .andExpect(jsonPath("$.feed.*", hasSize(20)))
//         check if the offset feeditem is the the 25
                 .andExpect(jsonPath("$.feed[0].id", is(25)));


    }

}