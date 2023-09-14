package com.parjalRai.films.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.WatchList;
import com.parjalRai.films.model.dto.WatchListDTO;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.WatchListService;

@RestController
@RequestMapping("/api/v1/watchList")
@CrossOrigin(origins = "http://localhost:3000")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private UserEntityRepository userRepository;

    @GetMapping("/user/{username}")
    public ResponseEntity<List<WatchList>> getUserWatchList(@PathVariable String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        UserEntity user = optUser.get();
         return ResponseEntity.ok(watchListService.findWatchListOfAUser(user));

    }

    @PostMapping
    public ResponseEntity<WatchList> createWatchList(@RequestBody WatchListDTO watchListDTO) {
        try {

            WatchList watchList = watchListService.createWatchList(watchListDTO.username(), watchListDTO.filmTitle());

            return ResponseEntity.ok(watchList);

        } catch (NotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWatchList(@PathVariable ObjectId id) {
        boolean deleted = watchListService.deleteWatchList(id);

        if (deleted) {

            return ResponseEntity.ok("Film deleted from watchList successfully");
            
        } else {
          return  ResponseEntity.notFound().build();
        }
    }



}
