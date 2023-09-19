package com.parjalRai.films.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Blocked;
import com.parjalRai.films.model.UserEntity;

public interface BlockedRepository extends MongoRepository<Blocked, ObjectId> {
    

    List<Blocked> findByBlocker(UserEntity blocker);

    boolean existsByBlockerandBlockedUser(UserEntity blocker, UserEntity blockedUser);
    
}
