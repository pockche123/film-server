package com.parjalRai.films.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.UserEntity;

public interface DiscussionRepository extends MongoRepository<Discussion, ObjectId> {


    List<Discussion> findByFilm(Film film);

    List<Discussion> findByUser(UserEntity user);
}
