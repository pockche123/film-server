package com.parjalRai.films.repository;



import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends MongoRepository<Film, ObjectId> {

    public Optional<Film> findFilmByImdbId(String imdbId);

    public Optional<Film> findFilmByTitleIgnoreCase(String title);



}
