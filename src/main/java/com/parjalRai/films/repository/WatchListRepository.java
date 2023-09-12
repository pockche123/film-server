package com.parjalRai.films.repository;

import java.util.List;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.WatchList;

public interface WatchListRepository extends MongoRepository<WatchList, ObjectId> {
    
    List<WatchList> findByUserEntity(UserEntity user);
    
}
