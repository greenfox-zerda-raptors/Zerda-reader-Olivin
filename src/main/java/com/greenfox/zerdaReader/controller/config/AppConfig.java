package com.greenfox.zerdaReader.controller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by zsofiaprincz on 01/02/17.
 */

@Configuration
public class AppConfig {

    private DriverManagerDataSource createPostgresDataSource(String profile) throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        String username = dbUri.getUserInfo().split(":")[0];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        if (profile.equals("prod")) {
            String password = dbUri.getUserInfo().split(":")[1];
            dbUrl += "?sslmode=require";
            dataSource.setPassword(password);
        }
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        return dataSource;
    }

}


