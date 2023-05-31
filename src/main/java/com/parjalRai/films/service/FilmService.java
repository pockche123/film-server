package com.parjalRai.films.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Review;
import com.parjalRai.films.repository.FilmRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    
    @Autowired
    private FilmRepository FilmRepository;
    
    public List<Film> findAllFilms() {
        return FilmRepository.findAll();
    }
    
    public Optional<Film> findAFilm(String imdbId) {
        return FilmRepository.findFilmByImdbId(imdbId);
    }

    public Optional<Film> findFilmByTitle(String title) {
        return FilmRepository.findFilmByTitleIgnoreCase(title);
    }

    // public List<Review> findReviewsByFilmTitle(String title) {
    //     Optional<Film> optionalFilm = FilmRepository.findFilmByTitleIgnoreCase(title);
    //     if (optionalFilm.isPresent()) {
    //         Film film = optionalFilm.get();
    //         return film.getReviews();
    //     } else {
    //         return Collections.emptyList();
    //     }
    // }


    

    

}


