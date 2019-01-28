package com.nastrsoft.comitChecks;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController()
@RequestMapping(path = "/api")
public class TestController {

    @RequestMapping(path = "/hello-world", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> helloWorld() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put("Access-Control-Allow-Origin", Arrays.asList("*"));
        return new ResponseEntity<>("Hello world!", headers, HttpStatus.OK);
    }

}
