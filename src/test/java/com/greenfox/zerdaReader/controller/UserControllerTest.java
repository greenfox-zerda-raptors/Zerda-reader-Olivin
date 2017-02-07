package com.greenfox.zerdaReader.controller;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.repository.UserRepository;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Rita on 2017-02-07.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZerdaReaderApplication.class)
@WebAppConfiguration
@DataJpaTest
@EnableWebMvc
public class UserControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void TestSuccessfulSignUp() throws Exception {
        mockMvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"example@gmail.com\", \"password\": \"12345\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.result", is("success")))
                .andExpect(jsonPath("$.token", is(userRepository.findOneByEmail("example@gmail.com").getToken())))
                .andExpect(jsonPath("$.id", is((int) (userRepository.findOneByEmail("example@gmail.com").getId()))));
    }

    @Test
    @Sql({"/clear-tables.sql", "/PopulateTables.sql"})
    public void TestUnsuccessfulSignUp() throws Exception {
        mockMvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"name@example.com\", \"password\": \"12345\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.result", is("fail")))
                .andExpect(jsonPath("$.message", is("email address already exists")));
    }
}