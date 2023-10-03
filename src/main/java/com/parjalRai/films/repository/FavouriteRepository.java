package com.parjalRai.films.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Favourite;

public interface FavouriteRepository extends MongoRepository<Favourite, ObjectId> {

    
    
}
