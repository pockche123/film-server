package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parjalRai.films.model.Review;
import com.parjalRai.films.model.ReviewLike;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.ReviewLikeRepository;
import com.parjalRai.films.repository.ReviewRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewLikeServiceTest {
    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private ReviewRepository reviewRepository;
    
    @Mock
    private ReviewLikeRepository reviewLikeRepository;
    
    @InjectMocks
    private ReviewLikeService reviewLikeService;
    
    private ReviewLike reviewLike1;
    private ReviewLike reviewLike2;

    @BeforeEach
    public void setUp() {
        reviewLike1 = new ReviewLike();
        reviewLike2 = new ReviewLike();
    }
    
    @AfterEach 
    public void tearDown() {
        reviewLike1 = null;
        reviewLike2 = null;
    }
    
    @Test
    public void findByReview_ReturnAllReviewsLike_WhenReviewFound() {
        Review Review = new Review();
        List<ReviewLike> expectedReviewLikes = Arrays.asList(reviewLike1, reviewLike2);
        when(reviewLikeRepository.findByReview(Review)).thenReturn(expectedReviewLikes);

        List<ReviewLike> actualReviewLikes = reviewLikeService.findByReview(Review);

        assertEquals(expectedReviewLikes, actualReviewLikes);
        verify(reviewLikeRepository).findByReview(Review);
    }

    @Test
    public void findByUserEntity_ReturnsAllReviewsLike_WhenReviewFound() {
        UserEntity user = new UserEntity();
        List<ReviewLike> expectedReviewLikes = Arrays.asList(reviewLike1, reviewLike2);
        when(reviewLikeRepository.findByUserEntity(user)).thenReturn(expectedReviewLikes);

        List<ReviewLike> actualReviewLikes = reviewLikeService.findByUserEntity(user);

        assertEquals(expectedReviewLikes, actualReviewLikes);
        verify(reviewLikeRepository).findByUserEntity(user);

    }


    @Test
    public void createReviewLike_ReturnsAReviewLike_WhenCreated() {
        ObjectId userId = new ObjectId();
        ObjectId ReviewId = new ObjectId();
        Review Review = new Review();
        UserEntity user = new UserEntity();
        when(reviewRepository.findById(ReviewId)).thenReturn(Optional.of(Review));
        when(userEntityRepository.findById(userId)).thenReturn(Optional.of(user));
        reviewLike1.setReview(Review);
        reviewLike1.setUserEntity(user);
        when(reviewLikeRepository.save(ArgumentMatchers.any(ReviewLike.class))).thenReturn(reviewLike1);

        ReviewLike actualReviewLike = reviewLikeService.createReviewLike(userId, ReviewId);

        assertEquals(reviewLike1, actualReviewLike);

    }

    @Test
    public void deletereviewLike_ReturnTrue_WhenreviewLikeDeleted() {
        ObjectId id = new ObjectId();
        reviewLike1.setId(id);
        when(reviewLikeRepository.findById(id)).thenReturn(Optional.of(reviewLike1));

        boolean result = reviewLikeService.deleteReviewLike(id);

        assertTrue(result);
        verify(reviewLikeRepository).delete(reviewLike1);
    }



    

}
