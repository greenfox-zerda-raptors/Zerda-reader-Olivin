package com.greenfox.zerdaReader.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Created by Rita on 2017-02-09.
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.greenfox.zerdaReader")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(myCustomFilter(), FilterSecurityInterceptor.class);

        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);
    }

    @Bean
    public AuthenticationTokenProcessingFilter myCustomFilter() {
        return new AuthenticationTokenProcessingFilter();
    }
}
