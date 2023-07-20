package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Review;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.ReviewRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private UserEntityRepository userRepository;

    @InjectMocks
    private ReviewService reviewService;

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
    public void testFindAllReviews() {

        review1.setReview("Incredible");
        review2.setReview("meh");

        // Arrange
        List<Review> expectedReviews = Arrays.asList(review1, review2);
        when(reviewRepository.findAll()).thenReturn(expectedReviews);

        // Act
        List<Review> actualReviews = reviewService.findAllReviews();

        // Assert
        assertEquals(expectedReviews.get(0).getReviewId(), actualReviews.get(0).getReviewId());
        assertEquals(expectedReviews.get(0).getReview(), actualReviews.get(0).getReview());
        assertEquals(expectedReviews.get(1).getReviewId(), actualReviews.get(1).getReviewId());
        assertEquals(expectedReviews.get(1).getReview(), actualReviews.get(1).getReview());

        // Verify
        verify(reviewRepository).findAll();
    }

    @Test
    public void testFindAReview_GetAReview_WhenAValidObjectIdPassed() {
        //Arrange
        ObjectId objectId = new ObjectId();
        // review1.setReviewId(objectId);
        when(reviewRepository.findById(objectId)).thenReturn(Optional.of(review1));

        //Act
        Optional<Review> optionalReview = reviewService.findAReview(objectId);
        Review expectedReview = optionalReview.get();

        //Assert
        assertEquals(expectedReview, review1);
        verify(reviewRepository).findById(objectId);
    }



    @Test
    public void testCreateReview_WithValidData_ShouldCreateReview() {
        // Arrange
        String filmTitle = "Django";
        String username = "JohnDoe";
        String reviewText = "Great movie!";
        int rating = 8;
        long likes = 4;
        Film film = new Film();
        film.setTitle(filmTitle);
        UserEntity user = new UserEntity();
        user.setUsername(username);

        Review review = new Review();

        review.setFilm(film);
        review.setUserEntity(user);
        review.setReview(reviewText);

        when(filmRepository.findFilmByTitleIgnoreCase(filmTitle)).thenReturn(Optional.of(film));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(reviewRepository.save(ArgumentMatchers.any(Review.class))).thenReturn(review);

        // Act
        Review actualReview = reviewService.createReview(filmTitle, username, reviewText, rating);

        assertEquals(review, actualReview);
        verify(reviewRepository, times(1)).save(any());
    }
    
    @Test
    public void testCreateReview_WithInvalidUsername_ShouldThrowNotFoundException() {
        // Arrange
        String filmTitle = "Film Title";
        String username = "Non-existent User";
        String reviewText = "Great movie!";
        int rating = 5;
        int likes = 4;
        Film film = new Film();
        when(filmRepository.findFilmByTitleIgnoreCase(anyString())).thenReturn(Optional.of(film));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class,
                () -> reviewService.createReview(filmTitle, username, reviewText, rating));

        verify(reviewRepository, never()).save(any());
    }
    
    @Test
    void deleteReview_WhenReviewExists_ShouldReturnTrue() {
        // Arrange
        ObjectId reviewId = new ObjectId();
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review1));

        // Act
        boolean result = reviewService.deleteReview(reviewId);

        // Assert
        assertTrue(result);
        verify(reviewRepository, times(1)).delete(review1);
    }

    @Test
    void deleteReview_WhenReviewDoesNotExist_ShouldReturnFalse() {
        // Arrange
        ObjectId reviewId = new ObjectId();
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        // Act
        boolean result = reviewService.deleteReview(reviewId);

        // Assert
        assertFalse(result);
        verify(reviewRepository, never()).delete(any());
    }


    @Test
    void updateReviewDetails_WhenReviewExists_ShouldReturnUpdatedReview() {
        // Arrange
        ObjectId reviewId = new ObjectId();
        Review existingReview = new Review();
        existingReview.setReview("Old review");

        Review updatedReview = new Review();
        updatedReview.setReview("New review");

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(existingReview)).thenReturn(updatedReview);

        // Act
        Review result = reviewService.updateReviewDetails(updatedReview, reviewId);

        // Assert
        assertNotNull(result);
        assertEquals("New review", result.getReview());
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(1)).save(existingReview);
    }


    @Test
    void findReviewsByFilm_ShouldReturnListOfReviews() {
        // Arrange
        Film film = new Film();
        film.setId(new ObjectId());

        Review review1 = new Review();
        review1.setReviewId(new ObjectId());
        review1.setFilm(film);

        Review review2 = new Review();
        review2.setReviewId(new ObjectId());
        review2.setFilm(film);

        when(reviewRepository.findByFilm(film)).thenReturn(Arrays.asList(review1, review2));

        // Act
        List<Review> result = reviewService.findReviewsByFilm(film);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(reviewRepository, times(1)).findByFilm(film);
    }


    @Test
    public void testFindreviewsByUserEntity_ReturnAListOfreviews_WhenFoundByFilm() {
        // Arrange
        UserEntity user = new UserEntity();
        review1.setUserEntity(user);
        ;
        List<Review> expectedreviews = Arrays.asList(review1);
        when(reviewRepository.findByUserEntity(user)).thenReturn(expectedreviews);
        // Act
        List<Review> actualreviews = reviewService.findReviewsByUser(user);
        // Assert
        assertEquals(expectedreviews, actualreviews);
        verify(reviewRepository, times(1)).findByUserEntity(user);
    }
    



    


    




}
