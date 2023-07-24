package com.parjalRai.films.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.service.UserEntityService;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins="http://localhost:3000")
public class UserEntityController {
    
    @Autowired
    private UserEntityService userEntityService;
    
    @GetMapping("/{username}")
    public ResponseEntity<Optional<UserEntity>> getAUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userEntityService.findByUsername(username));
    }



}
