package com.parjalRai.films.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Review;
import com.parjalRai.films.model.ReviewLike;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.ReviewLikeRepository;
import com.parjalRai.films.repository.ReviewRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@Service
public class ReviewLikeService {

    @Autowired
    private UserEntityRepository userEntityRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private ReviewLikeRepository reviewLikeRepo;

    public List<ReviewLike> findByReview(Review review) {
        return reviewLikeRepo.findByReview(review);
    }

    public List<ReviewLike> findByUserEntity(UserEntity user) {
        return reviewLikeRepo.findByUserEntity(user);
    }

    public ReviewLike createReviewLike(ObjectId userId, ObjectId reviewId) {

        ReviewLike reviewLike = new ReviewLike();

        Optional<Review> optReview = reviewRepo.findById(reviewId);
        Optional<UserEntity> optUser = userEntityRepo.findById(userId);

        Review review = optReview.orElseThrow(() -> new NotFoundException("Review not found"));
        UserEntity user = optUser.orElseThrow(() -> new NotFoundException("User not found"));

        reviewLike.setReview(review);
        reviewLike.setUserEntity(user);
        reviewLike.setTimestamp(Instant.now());
        if (!reviewLikeRepo.existsById(reviewLike.getId())) {
            return reviewLikeRepo.save(reviewLike);
        }
        return null;
    }

    public boolean deleteReviewLike(ObjectId id) {
        return reviewLikeRepo.findById(id).map(like -> {
            reviewLikeRepo.delete(like);
            return true;
        }).orElse(false);

    }

}
