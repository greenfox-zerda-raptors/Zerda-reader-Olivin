package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by Rita on 2017-01-23.
 */
@Component
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateResponseForSignUp(boolean successful, User user) {
        if (successful) {
            return "{\"result\": \"success\", \"token\": \"" + user.getToken() + "\", \"id\": " + user.getId() + "}";
        } else {
            return "{\"result\": \"fail\", \"message\": \"email address already exists\"}";
        }
    }

    public String generateResponseForLogin(String email, String password) {
        if (isExistingEmail(email) && isMatchingPassword(email, password)) {
            User user = userRepository.findOneByEmail(email);
            generateNewToken(user);
            user = userRepository.save(user);
            return "{\"result\": \"success\", \"token\": \"" + user.getToken() + "\", \"id\": " + user.getId() + "}";
        } else {
            return "{\"result\": \"fail\", \"message\": \"invalid username or password\"}";
        }
    }

    private void generateNewToken(User user) {
        List<String> tokens;
        do {
            tokens = userRepository.getAllUserTokens();
            user.setToken(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        } while (tokens.contains(user.getToken()));
    }

    public User addNewUser(String email, String password) {
        String encryptedPassword = passwordEncoder.encode(password);
        User newUser = new User(email, encryptedPassword);
        if (isExistingToken(newUser.getToken())) {
            generateNewToken(newUser);
        }
        return userRepository.save(newUser);
    }

    public boolean isExistingEmail(String email) {
        return userRepository.findOneByEmail(email) != null;
    }

    public boolean isExistingToken(String token) {
        return userRepository.findOneByToken(token) != null;
    }

    public User getUserByToken(String token) {
        return userRepository.findOneByToken(token);
    }

    private boolean isMatchingPassword(String email, String password) {
        User user = getUserByEmail(email);
        return passwordEncoder.matches(password, user.getPassword());
    }

    public User getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
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
