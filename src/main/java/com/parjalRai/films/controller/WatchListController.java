package com.parjalRai.films.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.WatchList;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.WatchListService;

@RestController
@RequestMapping("/api/v1/watchList")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private UserEntityRepository userRepository;

    @GetMapping("/user/{username}")
    public List<WatchList> getUserWatchList(@PathVariable String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        UserEntity user = optUser.get();
        List<WatchList> result = watchListService.findWatchListOfAUser(user);

        return result;

    }

}
