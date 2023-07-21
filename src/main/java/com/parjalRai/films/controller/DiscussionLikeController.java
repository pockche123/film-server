package com.parjalRai.films.controller;

import java.util.List;
import java.util.Optional;

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

import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.DiscussionLike;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.dto.DiscussionLikeDTO;
import com.parjalRai.films.repository.DiscussionRepository;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.DiscussionLikeService;

@RestController
@RequestMapping("/api/v1/discussionLikes")
@CrossOrigin(origins = "http://localhost:3000")
public class DiscussionLikeController {

    @Autowired
    private DiscussionLikeService discussionLikeService;
    
    @Autowired
    private DiscussionRepository discussionRepository;
    
    @Autowired
    private UserEntityRepository userEntityRepository;

    @GetMapping("/discussion/{id}")
    public ResponseEntity<List<DiscussionLike>> getAllLikesByDiscussion(@PathVariable ObjectId id) {
        Optional<Discussion> optDiscussion = discussionRepository.findById(id);
        return ResponseEntity.ok(discussionLikeService.findByDiscussion(optDiscussion.get()));
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<List<DiscussionLike>> getAllLikesByUserEntity(@PathVariable ObjectId id) {
        Optional<UserEntity> optUser = userEntityRepository.findById(id);
        return ResponseEntity.ok(discussionLikeService.findByUserEntity(optUser.get()));

    }
    
    @PostMapping
    public ResponseEntity<DiscussionLike> createDiscussionLike(@RequestBody DiscussionLikeDTO DiscussionLikeDTO) {
        try {
            DiscussionLike DiscussionLike = discussionLikeService.createDiscussionLike(DiscussionLikeDTO.userId(),
                    DiscussionLikeDTO.discussionId());
            System.err.println(DiscussionLike);
            return ResponseEntity.ok(DiscussionLike);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDiscussionLike(@PathVariable ObjectId id) {
        boolean deleted = discussionLikeService.deleteDiscussionLike(id);
        if (deleted) {
            return ResponseEntity.ok("Review like deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    
}
