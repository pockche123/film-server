package com.parjalRai.films.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Follower;
import com.parjalRai.films.model.UserEntity;

import org.bson.types.ObjectId;
import java.util.List;




public interface FollowerRepository extends MongoRepository<Follower, ObjectId> {


    List<Follower> findByFollowingUser(UserEntity followingUser);

    boolean existsByFollowingUserAndFollower(UserEntity followingUser, UserEntity follower);

}
