package com.parjalRai.films.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Stream;

public interface StreamRepository extends MongoRepository<Stream, ObjectId> {
    List<Stream> findByFilm(Film film);
}
