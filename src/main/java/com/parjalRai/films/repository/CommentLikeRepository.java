package com.parjalRai.films.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.parjalRai.films.model.Comment;
import com.parjalRai.films.model.CommentLike;
import com.parjalRai.films.model.UserEntity;

public interface CommentLikeRepository extends MongoRepository<CommentLike, ObjectId> {

    List<CommentLike> findByUserEntity(UserEntity user);

    List<CommentLike> findByComment(Comment comment);

    boolean existsByUserEntityAndComment(UserEntity user, Comment comment);

}