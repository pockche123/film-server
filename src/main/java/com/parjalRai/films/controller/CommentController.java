package com.parjalRai.films.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parjalRai.films.model.Comment;
import com.parjalRai.films.model.dto.CommentDTO;
import com.parjalRai.films.service.CommentService;

@RestController
@RequestMapping("api/v1/comment")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok(commentService.findAllComments());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Comment>> getAllCommmentsByUser(String username) {
        return ResponseEntity.ok(commentService.findAllCommentsByUser(username));
    }

    @GetMapping("/dicussion/{discussionId}")
    public ResponseEntity<List<Comment>> getAllCommentsByDiscussion(ObjectId discussionId) {
        return ResponseEntity.ok(commentService.findAllCommentsByDiscussion(discussionId));
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO) {
        
    }


}
