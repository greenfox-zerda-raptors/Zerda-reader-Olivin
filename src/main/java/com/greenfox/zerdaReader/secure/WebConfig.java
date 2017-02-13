//package com.greenfox.zerdaReader.secure;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * Created by zoloe on 2017. 02. 13..
// */
//@Configuration
//@EnableWebMvc
//public class WebConfig extends WebMvcConfigurerAdapter{
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("PUT","POST","GET","DELETE", "OPTIONS");
//
//    }
//
//
////    emigy is lehet: http://stackoverflow.com/questions/18264334/cross-origin-resource-sharing-with-spring-security
////
////    @Configuration
////    @EnableWebMvc
////    public class CorsConfiguration extends WebMvcConfigurerAdapter {
////
////        @Override
////        public void addCorsMappings(CorsRegistry registry) {
////            registry.addMapping("/**");
////        }
////    }
//
//}
