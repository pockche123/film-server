package com.parjalRai.films.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/check")
@CrossOrigin(origins = { "http://localhost:3000" })
public class CheckController {
    
    @GetMapping
    public ResponseEntity<String> sayHey() {
        return ResponseEntity.ok().body("Hey, you got it.");
    }

}
