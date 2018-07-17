package com.appsbyram.oscon2018.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private GlobalProperties properties;

    @RequestMapping("/")
    public ResponseEntity<Integer> home() {
        return new ResponseEntity<>(properties.getInterval(), HttpStatus.OK);
    }

}
