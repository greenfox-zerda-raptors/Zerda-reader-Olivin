package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Rita on 2017-01-23.
 */
@Component
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getUser(Long id) {
        return userRepository.findOne(id);
    }
    public User getFirstUser(){
        return userRepository.findOne(userRepository.getAllUserId().get(0));
    }

    public User getUserById(Long id){
        return userRepository.findOne(id);
    }
}
