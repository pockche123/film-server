package com.parjalRai.films.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Review;

public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

    List<Review> findByFilm(Film film);

}
