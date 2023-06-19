package com.parjalRai.films.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.dto.DiscussionDTO;
import com.parjalRai.films.service.DiscussionService;

@RestController
@RequestMapping("api/v1/discussion")
@CrossOrigin(origins = "http://localhost:3000")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @GetMapping
    public ResponseEntity<List<Discussion>> getAllDiscussions() {
        return ResponseEntity.ok(discussionService.getAllDiscussions());
    }

    @GetMapping("/film/{filmTitle}")
    public ResponseEntity<List<Discussion>> getDiscussionsByFilm(@PathVariable String filmTitle) {
        return ResponseEntity.ok(discussionService.getDiscussionsByFilm(filmTitle));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Discussion>> getDiscussionsByUser(@PathVariable String username) {
        return ResponseEntity.ok(discussionService.getDiscussionsByUser(username));
    }

    @PostMapping
    public ResponseEntity<Discussion> createDiscussion(@RequestBody DiscussionDTO discussionDTO) {
        try {
            Discussion discussion = discussionService.createDiscussion(discussionDTO.filmTitle(),
                    discussionDTO.username(), discussionDTO.title(), discussionDTO.description(),
                    discussionDTO.likes());
            return ResponseEntity.ok(discussion);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Discussion> updateDiscussion(@RequestBody Discussion discussion, @PathVariable ObjectId id,
            Authentication authentication) {
        Discussion updatedDiscussion = discussionService.updateDiscussionDetails(discussion, id);

        String username = authentication.getName();
        if (updatedDiscussion != null && discussionService.isTheOwner(id, username)) {
            return ResponseEntity.ok(updatedDiscussion);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDiscussion(@PathVariable ObjectId id, Authentication authentication) {
        String username = authentication.getName();

        if (discussionService.isTheOwner(id, username)) {
            boolean deleted = discussionService.deleteDiscussion(id);
            if (deleted) {
                return ResponseEntity.ok("Discussion deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }

        } else {
            return ResponseEntity.notFound().build();
        }

    }

}