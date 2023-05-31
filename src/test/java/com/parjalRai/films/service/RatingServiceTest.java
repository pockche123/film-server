package com.parjalRai.films.service;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Rating;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.RatingRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private UserEntityRepository userRepository;
    
    @Mock
    private FilmRepository filmRepository;
    
    @InjectMocks
    private RatingService ratingService;

    private Rating rating1;
    private Rating rating2; 

    @BeforeEach
    public void setup() {
        rating1 = new Rating();
        rating2 = new Rating();
    }


    @AfterEach
    public void tearDown() {
        rating1 = null;
        rating2 = null;
    }

    @Test
    public void testFindAllRatings_ReturnAListOfRatings_WhenFound() {
        //Arrange
        List<Rating> expectedRatings = Arrays.asList(rating1, rating2);
        when(ratingRepository.findAll()).thenReturn(expectedRatings);
        //Act
        List<Rating> actualRatings = ratingService.findAllRatings();
        //Assert
        assertEquals(expectedRatings, actualRatings);

        verify(ratingRepository).findAll();
    }

    @Test
    public void testFindRatingsByFilm_ReturnAListOfRatings_WhenFoundByFilm() {
        //Arrange
        Film film = new Film();
        rating1.setFilm(film);
        List<Rating> expectedRatings = Arrays.asList(rating1);
        when(ratingRepository.findByFilm(film)).thenReturn(expectedRatings);
        //Act
        List<Rating> actualRatings = ratingService.findRatingsByFilm(film);
        //Assert
        assertEquals(expectedRatings, actualRatings);
        verify(ratingRepository, times(1)).findByFilm(film);
    }

    @Test
    public void testFindRatingsByUserEntity_ReturnAListOfRatings_WhenFoundByFilm() {
        //Arrange
        UserEntity user = new UserEntity();
        rating1.setUserEntity(user);;
        List<Rating> expectedRatings = Arrays.asList(rating1);
        when(ratingRepository.findByUserEntity(user)).thenReturn(expectedRatings);
        //Act
        List<Rating> actualRatings = ratingService.findRatingsByUser(user);
        //Assert
        assertEquals(expectedRatings, actualRatings);
        verify(ratingRepository, times(1)).findByUserEntity(user);
    }

    








    @Test
    public void testCreateRating_WithValidData_ShouldCreateARating() {
        //Arrange
        String filmTitle = "Django";
        String username = "JaneDoe";
        int rating = 8;
        Film film = new Film();
        film.setTitle(filmTitle);
        UserEntity user = new UserEntity();
        user.setUsername(username);

        rating1.setFilm(film);
        rating1.setUserEntity(user);
        rating1.setRating(rating);

        when(filmRepository.findFilmByTitleIgnoreCase(filmTitle)).thenReturn(Optional.of(film));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(ratingRepository.save(rating1)).thenReturn(rating1);

        //Act
        Rating actualRating = ratingService.createRating(filmTitle, username, rating);

        //Assert
        assertEquals(rating1, actualRating);
        verify(ratingRepository).save(rating1);

    }

    @Test
    public void testDeleteRating_WhenRatingExists_ShouldReturnTrue() {
        //Arrange
        ObjectId ratingId = new ObjectId();
        rating1.setRatingId(ratingId);
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating1));
        //Act
        boolean result = ratingService.deleteRating(ratingId);
        //Assert
        assertTrue(result);
        verify(ratingRepository).delete(rating1);

    }
    
    @Test
    public void testDeleteRating_WhenRatingDoesNotExist_ShouldReturnFalse() {
        // Arrange
        ObjectId ratingId = new ObjectId();
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty());
        // Act
        boolean result = ratingService.deleteRating(ratingId);
        // Assert
        assertFalse(result);
        verify(ratingRepository, never()).delete(rating1);
    }

    @Test
    public void updateRatingDetails_WhenRatingExists_ShouldReturnUpdatedReview() {
        //Arrange
        ObjectId ratingId = new ObjectId();
        Rating existingRating = new Rating();
        existingRating.setRating(8);
        Rating updatedRating = new Rating();
        updatedRating.setRating(6);
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(existingRating));
        when(ratingRepository.save(existingRating)).thenReturn(updatedRating);

        //Act
        Rating result = ratingService.updateratingDetails(updatedRating, ratingId);

        //Assert
        assertNotNull(result);
        assertEquals(existingRating.getRating(), result.getRating());
        verify(ratingRepository).findById(ratingId);
        verify(ratingRepository).save(existingRating);

    }
    



    

    
}
