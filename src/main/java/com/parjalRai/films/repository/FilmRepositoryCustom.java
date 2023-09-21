package com.parjalRai.films.repository;

import java.util.List;

import com.parjalRai.films.model.Film;

public interface FilmRepositoryCustom {
    List<Film> findTop4FilmsByAverageRating();
}
