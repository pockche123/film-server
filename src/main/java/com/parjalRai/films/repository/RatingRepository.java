package com.parjalRai.films.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.AverageRatingResult;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Rating;
import com.parjalRai.films.model.UserEntity;

public interface RatingRepository extends MongoRepository<Rating, ObjectId> {
    List<Rating> findByFilm(Film film);
    
    List<Rating> findByUserEntity(UserEntity user);



    @Aggregation(pipeline = {
            "{$match: {'film.title': ?0}}",
              "{$group: {_id: '$film', averageRating: {$avg: '$rating'}}}"
        
    })
    List<AverageRatingResult> getAverageRatingForFilmByTitle(String title);



}
