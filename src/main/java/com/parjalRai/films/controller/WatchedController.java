package com.parjalRai.films.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.Watched;
import com.parjalRai.films.model.dto.WatchedDTO;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.WatchedService;

@RestController
@RequestMapping("/api/v1/watched")
@CrossOrigin(origins = "http://localhost:3000")
public class WatchedController {

    @Autowired
    private WatchedService watchedService;
    
    @Autowired
    private UserEntityRepository userRepository;

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Watched>> getUserWatched(@PathVariable String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        UserEntity user = optUser.get();
        return ResponseEntity.ok(watchedService.findWatchedOfAUser(user));
    }

    @PostMapping
    public ResponseEntity<Watched> createWatched(@RequestBody WatchedDTO watchedDTO) {
        try {

            Watched watchList = watchedService.createWatched(watchedDTO.username(), watchedDTO.filmTitle());

            return ResponseEntity.ok(watchList);

        } catch (NotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWatched(@PathVariable ObjectId id) {
        boolean deleted = watchedService.deleteWatched(id);

        if (deleted) {

            return ResponseEntity.ok("Film deleted from watched successfully");

        } else {
            return ResponseEntity.notFound().build();
        }
    }



    
    
}
