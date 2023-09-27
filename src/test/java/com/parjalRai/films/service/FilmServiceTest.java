package com.parjalRai.films.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Rating;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.RatingRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private RatingRepository ratingRepository; 

    @InjectMocks
    private FilmService filmService;

    @Test
    void findAllFilms_ReturnsListOfFilms() {
        // Mocking variables
        List<Film> films = new ArrayList<>();
        films.add(new Film());
        films.add(new Film());

        // Mocking repository method
        when(filmRepository.findAll()).thenReturn(films);

        // Call the method to be tested
        List<Film> result = filmService.findAllFilms();

        // Verify that the necessary method was called
        verify(filmRepository).findAll();

        // Assert the result
        assertEquals(films, result);
    }

    @Test
    void findAFilm_ReturnsAFilm_WhenImdbIdIsPassed() {
        Film film1 = new Film();

        film1.setImdbId("exampleId");
        Optional<Film> optionalFilm = Optional.of(film1);

        when(filmRepository.findFilmByImdbId("exampleId")).thenReturn(optionalFilm);

        Optional<Film> resultFilm = filmService.findAFilm("exampleId");

        verify(filmRepository).findFilmByImdbId("exampleId");
        assertEquals(optionalFilm, resultFilm);

    }

    @Test
    void findAFilm_ReturnsAFilm_WhenFilmTitleIsFound() {
        Film film1 = new Film();

        film1.setTitle("title");
        Optional<Film> optionalFilm = Optional.of(film1);

        when(filmRepository.findFilmByTitleIgnoreCase("title")).thenReturn(optionalFilm);

        Optional<Film> resultFilm = filmService.findFilmByTitle("title");

        verify(filmRepository).findFilmByTitleIgnoreCase("title");

        assertEquals(optionalFilm, resultFilm);
    }


    @Test
    void test_findTopRatedFilms_ReturnsAListOfFilms() {
        

        Film film1 = new Film();
        Film film2 = new Film();
        Film film3 = new Film(); 
        
     
        Rating rating1 = new Rating();
        rating1.setFilm(film1);
        rating1.setRating(5);
        Rating rating2 = new Rating();
        rating2.setFilm(film2);
        rating2.setRating(5); 
        Rating rating3 = new Rating();
        rating3.setFilm(film3);
        rating3.setRating(7);

        List<Rating> ratings = Arrays.asList(rating1, rating2, rating3);

        when(ratingRepository.findAll()).thenReturn(ratings);

        // Act
        List<Film> topRatedFilms = filmService.findTopRatedFilms();

        System.err.println(topRatedFilms);

        // Assert
        // assertEquals(3, topRatedFilms.size());
    
        assertEquals(ratings, topRatedFilms);
    

    }

    // @Test
    // void findReviewsByFilmTitle_ReturnsReviews_WhenFilmTitleIsFound() {
    //     Film film1 = new Film();
    //     String title = "title";
    //     film1.setTitle(title);
    //     Review review1 = new Review();
    //     Review review2 = new Review();

    //     film1.setReviews(Arrays.asList(review1, review2));
    //     Optional<Film> optionalFilm = Optional.of(film1);

    //     when(filmRepository.findFilmByTitleIgnoreCase(title)).thenReturn(optionalFilm);

    //     // Act
    //     List<Review> actualReviews = filmService.findReviewsByFilmTitle(title);

    //     // Assert
    //     assertEquals(film1.getReviews(), actualReviews);
    //     verify(filmRepository, times(1)).findFilmByTitleIgnoreCase(title);
    // }

    // @Test
    // void findReviewsByFilmTitle_ReturnsEmptyList_WhenFilmTitleIsNotFound() {
    //     // Arrange
    //     String title = "No title";
    //     Optional<Film> optionalFilm = Optional.empty();

    //     when(filmRepository.findFilmByTitleIgnoreCase(title)).thenReturn(optionalFilm);

    //     // Act
    //     List<Review> actualReviews = filmService.findReviewsByFilmTitle(title);

    //     // Assert
    //     assertEquals(Collections.emptyList(), actualReviews);
    //     verify(filmRepository, times(1)).findFilmByTitleIgnoreCase(title);
    // }

}
