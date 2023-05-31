package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Review;
import com.parjalRai.films.model.dto.ReviewDTO;
import com.parjalRai.films.service.FilmService;
import com.parjalRai.films.service.ReviewService;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @Mock
    private FilmService filmService;

    @InjectMocks
    private ReviewController reviewController;

    private Review review1;
    private Review review2;
    
    @BeforeEach
    public void setUp() {
        review1 = new Review();
        review2 = new Review();
    }

    @AfterEach
    public void tearDown() {
        review1 = null;
        review2 = null;
    }

    @Test
    void getAllReviews_ShouldReturnListOfReviews() {
        // Arrange

        List<Review> reviews = Arrays.asList(review1, review2);

        when(reviewService.findAllReviews()).thenReturn(reviews);

        // Act
        ResponseEntity<List<Review>> response = reviewController.getAllReviews();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviews, response.getBody());
        verify(reviewService, times(1)).findAllReviews();
    }


    @Test
    void deleteReview_WhenReviewExists_ShouldReturnOkResponse() {
        // Arrange
        ObjectId reviewId = new ObjectId();
        when(reviewService.deleteReview(reviewId)).thenReturn(true);

        // Act
        ResponseEntity<String> response = reviewController.deleteReview(reviewId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Review deleted successfully", response.getBody());
        verify(reviewService, times(1)).deleteReview(reviewId);
    }

    @Test
    void deleteReview_WhenReviewDoesNotExist_ShouldReturnNotFoundResponse() {
        // Arrange
        ObjectId reviewId = new ObjectId();
        when(reviewService.deleteReview(reviewId)).thenReturn(false);

        // Act
        ResponseEntity<String> response = reviewController.deleteReview(reviewId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(reviewService, times(1)).deleteReview(reviewId);
    }

    @Test
    void createReview_WhenReviewCreated_ShouldReturnOkResponse() {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO("Film Title", "Username", "Review");

        Review createdReview = new Review();
        createdReview.setReviewId(new ObjectId());
        createdReview.setReview(reviewDTO.review());

        when(reviewService.createReview(reviewDTO.filmTitle(), reviewDTO.username(), reviewDTO.review()))
                .thenReturn(createdReview);

        // Act
        ResponseEntity<Review> response = reviewController.createReview(reviewDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdReview, response.getBody());
        verify(reviewService, times(1)).createReview(reviewDTO.filmTitle(), reviewDTO.username(), reviewDTO.review());
    }


    @Test
    void updateReview_WhenReviewUpdated_ShouldReturnOkResponse() {
        // Arrange
        ObjectId reviewId = new ObjectId();
        Review updatedReview = new Review();
        updatedReview.setReviewId(reviewId);
        updatedReview.setReview("Updated review");

        when(reviewService.updateReviewDetails(updatedReview, reviewId)).thenReturn(updatedReview);

        // Act
        ResponseEntity<Review> response = reviewController.updateReview(updatedReview, reviewId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedReview, response.getBody());
        verify(reviewService, times(1)).updateReviewDetails(updatedReview, reviewId);
    }


    @Test
    void getFilmReviews_WhenFilmExists_ShouldReturnListOfReviews() {
        // Arrange
        String filmTitle = "Film Title";

        Film film = new Film();
        film.setTitle(filmTitle);

        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(new Review());
        expectedReviews.add(new Review());

        Optional<Film> optionalFilm = Optional.of(film);

        when(filmService.findFilmByTitle(filmTitle)).thenReturn(optionalFilm);
        when(reviewService.findReviewsByFilm(film)).thenReturn(expectedReviews);

        // Act
        List<Review> result = reviewController.getFilmReviews(filmTitle);

        // Assert
        assertNotNull(result);
        assertEquals(expectedReviews, result);
        verify(filmService, times(1)).findFilmByTitle(filmTitle);
        verify(reviewService, times(1)).findReviewsByFilm(film);
    }


    
}
