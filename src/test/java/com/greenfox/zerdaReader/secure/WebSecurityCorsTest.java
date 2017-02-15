package com.greenfox.zerdaReader.secure;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.Charset;
import java.util.Collection;

import static org.hamcrest.Matchers.anything;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;


/**
 * Created by zoloe on 2017. 02. 14..
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ZerdaReaderApplication.class)
@WebAppConfiguration
@DataJpaTest
@EnableWebMvc
//@ContextConfiguration
public class WebSecurityCorsTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

//    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(filterChainProxy)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void TestIfCorsHeaderIsPresent() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"example@gmail.com\", \"password\": \"12345\"}"))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, anything()))
                .andExpect(header().string("rst",anything()))
                .andReturn();
        Collection<String> content = result.getResponse().getHeaderNames();
//        Arrays<String> e = ;
        System.out.println(content.toArray());
    }
}
