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

import com.parjalRai.films.model.Comment;
import com.parjalRai.films.model.CommentLike;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.dto.CommentLikeDTO;
import com.parjalRai.films.repository.CommentRepository;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.CommentLikeService;

@RestController
@RequestMapping("/api/v1/commentLikes")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentLikeController {
    

    @Autowired
    private CommentLikeService commentLikeService;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private UserEntityRepository userEntityRepository;
    

    @GetMapping("/comment/{id}")
    public ResponseEntity<List<CommentLike>> getAllLikesByComment(@PathVariable ObjectId id) {
        Optional<Comment> optcomment = commentRepository.findById(id);
        return ResponseEntity.ok(commentLikeService.findByComment(optcomment.get()));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CommentLike>> getAllLikesByUserEntity(@PathVariable ObjectId id) {
        Optional<UserEntity> optUser = userEntityRepository.findById(id);
        return ResponseEntity.ok(commentLikeService.findByUserEntity(optUser.get()));

    }

    @PostMapping
    public ResponseEntity<CommentLike> createCommentLike(@RequestBody CommentLikeDTO commentLikeDTO) {
        try {
            CommentLike commentLike = commentLikeService.createCommentLike(commentLikeDTO.userId(),
                    commentLikeDTO.commentId());
            System.err.println(commentLike);
            return ResponseEntity.ok(commentLike);
        } catch (Exception e) {
              System.err.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletecommentLike(@PathVariable ObjectId id) {
        boolean deleted = commentLikeService.deleteCommentLike(id);
        if (deleted) {
            return ResponseEntity.ok("Comment like deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
