package com.parjalRai.films.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Review;
import com.parjalRai.films.model.ReviewLike;
import com.parjalRai.films.model.UserEntity;

public interface ReviewLikeRepository extends MongoRepository<ReviewLike, ObjectId> {

    List<ReviewLike> findByReview(Review review);

    List<ReviewLike> findByUserEntity(UserEntity userEntity);

    boolean existsByUserEntityAndReview(UserEntity userEntity, Review review);
    

    
}
