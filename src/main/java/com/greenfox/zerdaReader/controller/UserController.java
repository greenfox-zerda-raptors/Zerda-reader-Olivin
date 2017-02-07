package com.greenfox.zerdaReader.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
        ObjectNode answer = mapper.createObjectNode();
        if (!userService.isExistingEmail(request.get("email").textValue())) {
            userService.addNewUser(request.get("email").textValue(), request.get("password").textValue());
            answer.put("result", "success")
                    .put("token", userService.getTokenForUser(request.get("email").textValue()))
                    .put("id", userService.getIDForUser(request.get("email").textValue()));
        } else {
            answer.put("result", "fail")
                    .put("message", "email address already exists");
        }
        return new ResponseEntity<JsonNode>(answer, HttpStatus.OK);
    }
}
