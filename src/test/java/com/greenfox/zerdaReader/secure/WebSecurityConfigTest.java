//package com.greenfox.zerdaReader.secure;
//
//import com.greenfox.zerdaReader.repository.UserRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
//import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
//import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
//import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
//import org.springframework.boot.test.util.EnvironmentTestUtils;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockServletContext;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//
//import java.nio.charset.Charset;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
//
///**
// * Created by zoloe on 2017. 02. 14..
// */
////https://github.com/spring-projects/spring-boot/blob/master/spring-boot-actuator/src/test/java/org/springframework/boot/actuate/endpoint/mvc/MvcEndpointCorsIntegrationTests.java
//public class WebSecurityConfigTest {
//
//    private AnnotationConfigWebApplicationContext context;
//
//    @Before
//    public void createContext() {
//        this.context = new AnnotationConfigWebApplicationContext();
//        this.context.setServletContext(new MockServletContext());
//        this.context.register(
//                JacksonAutoConfiguration.class,
//                HttpMessageConvertersAutoConfiguration.class,
////                EndpointAutoConfiguration.class, EndpointWebMvcAutoConfiguration.class,
//                PropertyPlaceholderAutoConfiguration.class,
////                AuditAutoConfiguration.class,
////                JolokiaAutoConfiguration.class,
//                WebMvcAutoConfiguration.class);
//    }
//
//    private MockMvc createMockMvc() {
//        this.context.refresh();
//        return MockMvcBuilders.webAppContextSetup(this.context).build();
//    }
//    private ResultActions performAcceptedCorsRequest() throws Exception {
//        return performAcceptedCorsRequest("/beans");
//    }
//
//    private ResultActions performAcceptedCorsRequest(String url) throws Exception {
//        return createMockMvc()
//                .perform(options(url).header(HttpHeaders.ORIGIN, "foo.example.com")
//                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET"))
//                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
//                        "foo.example.com"));
////                .andExpect(status().isOk());
//    }
//
//
//
//    @Test
//    public void corsIsDisabledByDefault() throws Exception {
//        createMockMvc()
//                .perform(options("/beans").header("Origin", "foo.example.com")
//                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET"))
//                .andExpect(
//                        header().doesNotExist(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
//    }
//
//    @Test
//    public void settingAllowedOriginsEnablesCors() throws Exception {
//        EnvironmentTestUtils.addEnvironment(this.context,
//                "endpoints.cors.allowed-origins:foo.example.com");
//        createMockMvc()
//                .perform(options("/beans").header("Origin", "bar.example.com")
//                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET"));
////                .andExpect(status().isForbidden());
//        performAcceptedCorsRequest();
//    }
//
//}
