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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parjalRai.films.model.Review;
import com.parjalRai.films.model.ReviewLike;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.dto.ReviewLikeDTO;
import com.parjalRai.films.repository.ReviewRepository;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.ReviewLikeService;

@RestController
@RequestMapping("/api/v1/reviewLikes")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewLikeController {


    @Autowired
    private ReviewLikeService reviewLikeService; 

    @Autowired
    private ReviewRepository reviewRepo;
    
    @Autowired
    private UserEntityRepository userRepo;
    
    
    @GetMapping("/review/{id}")
    public ResponseEntity<List<ReviewLike>> getAllLikesByReviews(@PathVariable ObjectId id) {
        Optional<Review> optReview = reviewRepo.findById(id);
        return ResponseEntity.ok(reviewLikeService.findByReview(optReview.get()));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ReviewLike>> getAllLikesByUserEntity(@PathVariable ObjectId id) {
        Optional<UserEntity> optUser = userRepo.findById(id);
        return ResponseEntity.ok(reviewLikeService.findByUserEntity(optUser.get()));
    }

    @PostMapping
    public ResponseEntity<ReviewLike> createReviewLike(ReviewLikeDTO reviewLikeDTO) {
        try {
            ReviewLike reviewLike = reviewLikeService.createReviewLike(reviewLikeDTO.userId(),
                    reviewLikeDTO.reviewId());
            return ResponseEntity.ok(reviewLike);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReviewLike(@PathVariable ObjectId id) {
        boolean deleted = reviewLikeService.deleteReviewLike(id);
        if (deleted) {
            return ResponseEntity.ok("Review like deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    
}
