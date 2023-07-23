package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.parjalRai.films.model.Review;
import com.parjalRai.films.model.ReviewLike;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.dto.ReviewLikeDTO;
import com.parjalRai.films.repository.ReviewRepository;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.ReviewLikeService;

@ExtendWith(MockitoExtension.class)
public class ReviewLikeControllerTest {
    
    @Mock
    private ReviewLikeService reviewLikeService;
    
    @Mock 
    private ReviewRepository reviewRepository;
    
    @Mock
    private UserEntityRepository userEntityRepository;

    @InjectMocks
    private ReviewLikeController reviewLikeController;
    
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
    public void getAllLikesByReview_ReturnsOkStatus_WhenAllReviewLikeReturns() {
        ObjectId id = new ObjectId();
        Review Review = new Review();
        when(reviewRepository.findById(id)).thenReturn(Optional.of(Review));
        List<ReviewLike> expected = Arrays.asList(reviewLike1, reviewLike2);
        when(reviewLikeService.findByReview(Review)).thenReturn(expected);

        ResponseEntity<List<ReviewLike>> response = reviewLikeController.getAllLikesByReviews(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(reviewLikeService).findByReview(Review);

    }

    @Test
    public void getAllLikesByUserEntity_ReturnsOkStatus_WhenAllReviewLikeReturns() {
        ObjectId id = new ObjectId();
        UserEntity user = new UserEntity();
        when(userEntityRepository.findById(id)).thenReturn(Optional.of(user));
        List<ReviewLike> expected = Arrays.asList(reviewLike1, reviewLike2);
        when(reviewLikeService.findByUserEntity(user)).thenReturn(expected);

        ResponseEntity<List<ReviewLike>> response = reviewLikeController.getAllLikesByUserEntity(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(reviewLikeService).findByUserEntity(user);

    }

    @Test
    public void createReviewLike_ReturnsOkStatus_WhenReviewLikeCreated() {
        ObjectId userId = new ObjectId();
        ObjectId ReviewId = new ObjectId();

        ReviewLikeDTO ReviewLikeDTO = new ReviewLikeDTO(userId, ReviewId);
        ReviewLike createdReviewLike = new ReviewLike();
        UserEntity user = new UserEntity();
        createdReviewLike.setUserEntity(user);
        when(reviewLikeService.createReviewLike(userId, ReviewId)).thenReturn(createdReviewLike);

        ResponseEntity<ReviewLike> response = reviewLikeController.createReviewLike(ReviewLikeDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdReviewLike, response.getBody());
        verify(reviewLikeService).createReviewLike(userId, ReviewId);

    }

    @Test
    public void testDeleteReview_ReturnsOkStatus_WhenDeleted() {
        ObjectId id = new ObjectId();
        when(reviewLikeService.deleteReviewLike(id)).thenReturn(true);

        ResponseEntity<String> response = reviewLikeController.deleteReviewLike(id);

        assertNotNull(response);
        assertEquals("Review like deleted successfully.", response.getBody());
        verify(reviewLikeService).deleteReviewLike(id);

    }

    

    


    

}