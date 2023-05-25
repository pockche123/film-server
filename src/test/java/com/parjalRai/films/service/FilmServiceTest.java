package com.parjalRai.films.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.repository.FilmRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

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

}
