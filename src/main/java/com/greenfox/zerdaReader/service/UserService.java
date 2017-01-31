package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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


    public User getUser(Long id){
        return userRepository.findOne(id);
    }
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

}
