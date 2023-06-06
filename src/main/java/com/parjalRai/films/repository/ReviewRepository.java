package com.parjalRai.films.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Review;
import com.parjalRai.films.model.UserEntity;

public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

    List<Review> findByFilm(Film film);

    List<Review> findByUserEntity(UserEntity user);

    Optional<Review> findByReviewIdTimestamp(long timestamp);
}
