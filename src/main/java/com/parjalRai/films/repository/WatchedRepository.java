package com.parjalRai.films.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.Watched;

public interface WatchedRepository extends MongoRepository<Watched, ObjectId> {
    
    List<Watched> findByUserEntity(UserEntity user);

    boolean existsByFilmAndUserEntity(Film film, UserEntity user);
    
}
