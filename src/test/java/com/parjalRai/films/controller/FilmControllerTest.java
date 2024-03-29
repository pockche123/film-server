package com.parjalRai.films.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Review;
import com.parjalRai.films.service.FilmService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {

    @Mock
    private FilmService filmService;

    @InjectMocks
    private FilmController filmController;

    @Test
    void getAllFilms_ReturnsListOfFilms() {

        List<Film> films = new ArrayList<>();
        films.add(new Film());
        films.add(new Film());

        when(filmService.findAllFilms()).thenReturn(films);

        ResponseEntity<List<Film>> responseEntity = filmController.getAllFilms();

        verify(filmService).findAllFilms();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(films, responseEntity.getBody());
    }

    @Test
    void getAFilm_ReturnsAFilm_WhenImdbIsCorrect() {
        Optional<Film> optionalFilm = Optional.of(new Film());
        optionalFilm = optionalFilm.map(film -> {
            film.setImdbId("exampleId");
            return film;
        });

        when(filmService.findAFilm("exampleId")).thenReturn(optionalFilm);

        ResponseEntity<Optional<Film>> responseEntity = filmController.getAFilm("exampleId");
        verify(filmService).findAFilm("exampleId");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(optionalFilm, responseEntity.getBody());
    }


    @Test
    void test_findTopRatedFilms_ReturnsAListOfFilms() {
        

        Film film1 = new Film();
        film1.setTitle("film1");
        Film film2 = new Film();
        film2.setTitle("film2");
        Film film3 = new Film();
        film3.setTitle("film3");

        List<Film> expected = Arrays.asList(film1, film2, film3);


        when(filmService.findTopRatedFilms()).thenReturn(expected);

        ResponseEntity<List<Film>> response = filmController.getTopRatedFilms();
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(filmService).findTopRatedFilms();
    
    }

    // @Test
    // void getFilmByTitle_ReturnsFilmWithHttpStatusOK() {

    //     String title = "title";
    //     Film film = new Film();
    //     Optional<Film> optionalFilm = Optional.of(film);

    //     when(filmService.findFilmByTitle(title)).thenReturn(optionalFilm);

    //     Film responseEntity = filmController.getFilmByTitle(title);

    //     verify(filmService).findFilmByTitle(title);

    //     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    //     assertEquals(optionalFilm, responseEntity.getBody());
    // }

    // @Test
    // void getReviewsByFilmTitle_ReturnsReviewsWithHttpStatusOK() {

    //     String title = "title";
    //     Review review1 = new Review();
    //     Review review2 = new Review();

    //     List<Review> expectedReviews = Arrays.asList(review1, review2);
    //     when(filmService.findReviewsByFilmTitle(title)).thenReturn(expectedReviews);
    //     ResponseEntity<List<Review>> responseEntity = filmController.getReviewsByFilmTitle(title);

    //     verify(filmService).findReviewsByFilmTitle(title);

    //     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    //     assertEquals(expectedReviews, responseEntity.getBody());
    // }
}
