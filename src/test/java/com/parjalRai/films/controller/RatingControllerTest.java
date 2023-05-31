package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Rating;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.dto.RatingDTO;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.FilmService;
import com.parjalRai.films.service.RatingService;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @Mock
    private FilmService filmService;

    @Mock
    private UserEntityRepository userRepository; 

    @InjectMocks
    private RatingController ratingController;

    private Rating rating1;
    private Rating rating2;

    @BeforeEach
    public void setUp() {
        rating1 = new Rating();
        rating2 = new Rating();
    }

    @AfterEach
    public void tearDown() {
        rating1 = null;
        rating2 = null;
    }

    @Test
    public void testGetAllRatings_ReturnAListOfRatings() {
        // Arrange
        List<Rating> ratings = Arrays.asList(rating1, rating2);
        when(ratingService.findAllRatings()).thenReturn(ratings);

        // Act
        ResponseEntity<List<Rating>> response = ratingController.getAllRatings();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ratings, response.getBody());
        verify(ratingService).findAllRatings();
    }

    @Test
    void deleteRating_WhenRatingExists_ShouldReturnTrue() {
        // Arrange
        ObjectId ratingId = new ObjectId();
        when(ratingService.deleteRating(ratingId)).thenReturn(true);

        // Act
        ResponseEntity<String> response = ratingController.deleterating(ratingId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Rating deleted successfully", response.getBody());
        verify(ratingService).deleteRating(ratingId);
    }

    @Test
    void deleteRating_WhenRatingDoesNotExist_ShouldReturnFalse() {
        //Arrange
        ObjectId ratingId = new ObjectId();
        when(ratingService.deleteRating(ratingId)).thenReturn(false);
        //Act
        ResponseEntity<String> response = ratingController.deleterating(ratingId);

        //Assert
        assertNotNull(response);
        assertNull(response.getBody());
        verify(ratingService).deleteRating(ratingId);
    }
    
    @Test
    void createRating_WhenRatingCreated_ShouldReturnOkResponse() {
        //Arrange
        RatingDTO ratingDTO = new RatingDTO("Django", "userJuan", 8);

        Rating createdRating = new Rating();
        createdRating.setRatingId(new ObjectId());
        createdRating.setRating(ratingDTO.rating());

        when(ratingService.createRating(ratingDTO.filmTitle(), ratingDTO.username(), ratingDTO.rating()))
                .thenReturn(createdRating);
        //Act
        ResponseEntity<Rating> response = ratingController.createrating(ratingDTO);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdRating, response.getBody());
        verify(ratingService).createRating(ratingDTO.filmTitle(), ratingDTO.username(), ratingDTO.rating());
    }

    @Test
    void updateRating_WhenRatingUPdated_ShouldReturnOkResponse() {
        //Arrange
        ObjectId ratingId = new ObjectId();
        Rating updatedRating = new Rating();

        updatedRating.setRatingId(ratingId);
        updatedRating.setRating(9);

        when(ratingService.updateratingDetails(updatedRating, ratingId)).thenReturn(updatedRating);

        // Act
        ResponseEntity<Rating> response = ratingController.updaterating(updatedRating, ratingId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRating, response.getBody());
        verify(ratingService, times(1)).updateratingDetails(updatedRating, ratingId);
    }
    
    @Test
    void getFilmRatings_WhenFilmExists_ShouldReturnListOfRatings() {
        //Arrange
        String filmTitle = "Django";
        Film film = new Film();
        film.setTitle(filmTitle);
        List<Rating> expectedRatings = Arrays.asList(rating1, rating2);

        when(filmService.findFilmByTitle(filmTitle)).thenReturn(Optional.of(film));
        when(ratingService.findRatingsByFilm(film)).thenReturn(expectedRatings);

        //Act
        List<Rating> results = ratingController.getFilmratings(filmTitle);

        //Assert
        assertNotNull(results);
        assertEquals(expectedRatings, results);
        verify(filmService).findFilmByTitle(filmTitle);
        verify(ratingService).findRatingsByFilm(film);
    }
    
    @Test
    void getUserRatings_WhenUserExists_ShouldReturnListOfRatings() {
        //Arrange
        String username = "jogo";
        UserEntity user = new UserEntity();
        user.setUsername(username);
        List<Rating> expectedRatings = Arrays.asList(rating1, rating2);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(ratingService.findRatingsByUser(user)).thenReturn(expectedRatings);

        //Act
        List<Rating> results = ratingController.getUserRatings(username);
        
        //Assert
        assertNotNull(results);
        assertEquals(expectedRatings, results);
        verify(ratingService).findRatingsByUser(user);
    }



}
