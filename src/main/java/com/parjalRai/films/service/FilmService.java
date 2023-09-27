package com.parjalRai.films.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Rating;
import com.parjalRai.films.model.Review;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.RatingRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private FilmRepository FilmRepository;

    @Autowired
    private RatingRepository ratingRepository;

    public List<Film> findAllFilms() {
        return FilmRepository.findAll();
    }

    public Optional<Film> findAFilm(String imdbId) {
        return FilmRepository.findFilmByImdbId(imdbId);
    }

    public Optional<Film> findFilmByTitle(String title) {
        return FilmRepository.findFilmByTitleIgnoreCase(title);
    }

    public List<Film> findTopRatedFilms() {

        List<Rating> ratings = ratingRepository.findAll();

        // Map<Film, Double> averageRatings = ratings.stream()
        //         .collect(Collectors.groupingBy(Rating::getFilm, Collectors.averagingDouble(Rating::getRating)));

        Map<Film, Double> averageRatings = ratings.stream()
                .collect(Collectors.groupingBy(Rating::getFilm, Collectors.averagingDouble(Rating::getRating)));

        List<Film> topRatedFilms = averageRatings.entrySet().stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()))
                .limit(4)
                .map(entry -> {
                    Film film = entry.getKey();
                    film.setAverageRating(entry.getValue());
                    return film;
                })
                .collect(Collectors.toList());



        return topRatedFilms;

    }

}
