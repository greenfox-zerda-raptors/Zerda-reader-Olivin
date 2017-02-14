package com.greenfox.zerdaReader.secure;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import com.greenfox.zerdaReader.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by zoloe on 2017. 02. 14..
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZerdaReaderApplication.class)
@WebAppConfiguration
@DataJpaTest
@EnableWebMvc
public class WebSecurityCorsTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

//    private MockMvc mockMvc;



    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(SpringSecurityCoreVersion).build();

//    @Before
//    public void setup() throws Exception {
//        this.mockMvc = webAppContextSetup(webApplicationContext).apply(spri)build();
//    }

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
