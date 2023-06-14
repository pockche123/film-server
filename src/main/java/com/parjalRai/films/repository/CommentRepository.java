package com.parjalRai.films.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Comment;
import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.UserEntity;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {

    List<Comment> findByUser(UserEntity user);
    
    List<Comment> findByDiscussion(Discussion discussion);
    
}
