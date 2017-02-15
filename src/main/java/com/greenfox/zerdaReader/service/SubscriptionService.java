package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.User;

/**
 * Created by zoloe on 2017. 02. 15..
 */
public class SubscriptionService {
    public String generateResponseForLogin(Long id){


//        if (isExistingEmail(email) && isMatchingPassword(email, password)) {
//            User user = userRepository.findOneByEmail(email);
//            generateNewToken(user);
//            user = userRepository.save(user);
//            return "{\"result\": \"success\", \"token\": \"" + user.getToken() + "\", \"id\": " + user.getId() + "}";
//        } else {
//            return "{\"result\": \"fail\", \"message\": \"invalid username or password\"}";
//        }

        return new String("E!");
    }
}
