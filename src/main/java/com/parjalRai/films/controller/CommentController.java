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

    @GetMapping("/parents")
    public ResponseEntity<List<Comment>> getAllParentComments() {
        return ResponseEntity.ok(commentService.findParentComments());
    }

    @GetMapping("/children")
    public ResponseEntity<List<Comment>> getAllChildComments() {
        return ResponseEntity.ok(commentService.findChildComments());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Comment>> getAllCommmentsByUser(@PathVariable String username) {
        return ResponseEntity.ok(commentService.findAllCommentsByUser(username));
    }

    @GetMapping("/discussion/{discussionId}")
    public ResponseEntity<List<Comment>> getAllCommentsByDiscussion(@PathVariable ObjectId discussionId) {
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
    public ResponseEntity<String> deleteComment(@PathVariable ObjectId id,
            Authentication authentication) {

        String username = authentication.getName();
        boolean deleted = commentService.deleteComment(id);

        if (commentService.isTheOwner(id, username)) {
            if (deleted) {
                return ResponseEntity.ok("Comment deleted successfully");
            } else {

                return ResponseEntity.notFound().build();
            }
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment, @PathVariable ObjectId id,
    Authentication authentication) {
        Comment updatedComment = commentService.updateCommentDetails(comment, id);
        String username = authentication.getName();
        if (updatedComment != null && commentService.isTheOwner(id, username)) {
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
