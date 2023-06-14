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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Comment;
import com.parjalRai.films.model.dto.CommentDTO;
import com.parjalRai.films.service.CommentService;

@RestController
@RequestMapping("api/v1/comment")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {
    
    private static final Optional<ObjectId> Optional = null;
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
        try {

            
            Comment comment = commentService.createComment(commentDTO.text(), commentDTO.discussionId(),
                    commentDTO.commentId(), commentDTO.username(), commentDTO.likes());
            return ResponseEntity.ok(comment);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable ObjectId id) {
        boolean deleted = commentService.deleteComment(id);
        if(deleted){
            return ResponseEntity.ok("Comment deleted successfully");
        } else 

            return ResponseEntity.notFound().build();
        }
        
    @PatchMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment, @PathVariable ObjectId id){
        Comment updatedComment = commentService.updateCommentDetails(comment, id);
        if(updatedComment != null){
            return ResponseEntity.ok(updatedComment);
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }     

    
       }





