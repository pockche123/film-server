package com.parjalRai.films.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.service.FilmService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void getFilmByTitle_ReturnsFilmWithHttpStatusOK() {

        String title = "title";
        Film film = new Film();
        Optional<Film> optionalFilm = Optional.of(film);

        when(filmService.findFilmByTitle(title)).thenReturn(optionalFilm);


        ResponseEntity<Optional<Film>> responseEntity = filmController.getFilmByTitle(title);

        verify(filmService).findFilmByTitle(title);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(optionalFilm, responseEntity.getBody());
    }
}
