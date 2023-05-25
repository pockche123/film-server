package com.parjalRai.films.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.UserEntity;

import java.util.Optional;

import org.bson.types.ObjectId;

public interface UserEntityRepository extends MongoRepository<UserEntity, ObjectId> {
    
    Optional<UserEntity> findByUsername(String username);
}
