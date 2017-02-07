package com.greenfox.zerdaReader.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public ResponseEntity<JsonNode> signup(@RequestBody String signupRequest) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree("{\"result\": \"success\", \"token\": \"ABC123\", \"id\": 431}");
        return new ResponseEntity<JsonNode>(jsonNode, HttpStatus.OK);
    }
}
