package com.parjalRai.films.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.parjalRai.films.model.Follower;
import com.parjalRai.films.model.dto.FollowerDTO;
import com.parjalRai.films.service.FollowerService;

@RestController
@RequestMapping("/api/v1/follower")
@CrossOrigin(origins = { "http://localhost:3000" })
public class FollowerController {


 
    @Autowired
    private FollowerService followerService;
    
    @GetMapping("/{username}")
    public ResponseEntity<List<Follower>> findByUserBeingFollowed(@PathVariable String username) {

        return new ResponseEntity<List<Follower>>(followerService.findByFollowingUser(username), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Follower> createFollower(@RequestBody FollowerDTO followerDTO) {

        try {

            Follower follower = followerService.createFollower(followerDTO.followedUsername(),
                    followerDTO.followingUsername());

            return ResponseEntity.ok(follower);

        } catch (NotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFollower(@PathVariable ObjectId id) {
        boolean deleted = followerService.deleteFollower(id);
        if (deleted) {
            return ResponseEntity.ok("Follower deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}


