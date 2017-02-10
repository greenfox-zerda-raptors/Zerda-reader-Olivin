package com.greenfox.zerdaReader.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Rita on 2017-02-07.
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/signup", method = RequestMethod.POST)
    public ResponseEntity<JsonNode> signUp(@RequestBody String signupRequest) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode request = mapper.readTree(signupRequest);
        JsonNode answer;
        if (!userService.isExistingEmail(request.get("email").textValue())) {
            User newUser = userService.addNewUser(request.get("email").textValue(), request.get("password").textValue());
            answer = mapper.readTree(userService.generateResponseForSignUp(true, newUser));
        } else {
            answer = mapper.readTree(userService.generateResponseForSignUp(false, null));
        }
        return new ResponseEntity<JsonNode>(answer, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<JsonNode> logIn(@RequestBody String loginRequest) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode request = mapper.readTree(loginRequest);
        String email = (request.get("email").textValue());
        String password = (request.get("password").textValue());
        JsonNode answer = mapper.readTree(userService.generateResponseForLogin(email, password));
        return new ResponseEntity<JsonNode>(answer, HttpStatus.OK);
    }
}
