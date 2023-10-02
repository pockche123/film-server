package com.parjalRai.films.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.DiscussionLike;
import com.parjalRai.films.model.UserEntity;

public interface DiscussionLikeRepository extends MongoRepository<DiscussionLike, ObjectId> {
  
    List<DiscussionLike> findByUserEntity(UserEntity user);

    List<DiscussionLike> findByDiscussion(Discussion discussion);

    boolean existsByUserEntityAndDiscussion(UserEntity user, Discussion discussion);
    
}
