package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by Rita on 2017-01-23.
 */
@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateResponseForSignUp(boolean successful, User user) {
        if (successful) {
            return "{\"result\": \"success\", \"token\": \"" + user.getToken() + "\", \"id\": " + user.getId() + "}";
        } else {
            return "{\"result\": \"fail\", \"message\": \"email address already exists\"}";
        }
    }

    public User addNewUser(String email, String password) {
        String encryptedPassword = passwordEncoder.encode(password);
        User newUser = new User(email, encryptedPassword);
        return userRepository.save(newUser);
    }

    public boolean isExistingEmail(String email) {
        return userRepository.findOneByEmail(email) != null;
    }

    public String getTokenForUser(String email) {
        return userRepository.findOneByEmail(email).getToken();
    }

    public Long getIDForUser(String email) {
        return userRepository.findOneByEmail(email).getId();
    }

    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    public User getFirstUser() {
        return userRepository.findOne(userRepository.getAllUserId().get(0));
    }

    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }
}
