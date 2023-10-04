package com.parjalRai.films.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Favourite;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.UserEntity;

public interface FavouriteRepository extends MongoRepository<Favourite, ObjectId> {



    List<Favourite> findByUserEntity(UserEntity userEntity);
    

    boolean existsByUserEntityAndFilm(UserEntity user, Film film);

}
