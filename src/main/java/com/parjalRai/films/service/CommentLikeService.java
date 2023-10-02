package com.parjalRai.films.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.DuplicateException;
import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Comment;
import com.parjalRai.films.model.CommentLike;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.CommentLikeRepository;
import com.parjalRai.films.repository.CommentRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@Service
public class CommentLikeService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    

    public List<CommentLike> findByComment(Comment comment) {
        return commentLikeRepository.findByComment(comment);
    }

    public List<CommentLike> findByUserEntity(UserEntity user) {
        return commentLikeRepository.findByUserEntity(user);
    }

    public CommentLike createCommentLike(ObjectId userId, ObjectId commentId) {
        CommentLike commentLike = new CommentLike();

        Optional<Comment> optComment = commentRepository.findById(commentId);
        Optional<UserEntity> optUser = userEntityRepository.findById(userId);

        Comment comment = optComment.orElseThrow(() -> new NotFoundException("Comment not found"));
        UserEntity user = optUser.orElseThrow(() -> new NotFoundException("User not found"));


        if (commentLikeRepository.existsByUserEntityAndComment(user, comment)) {
            throw new DuplicateException("This comment has already been liked by the user");
        }

        commentLike.setComment(comment);
        commentLike.setUserEntity(user);
        commentLike.setTimestamp(Instant.now());

        return commentLikeRepository.save(commentLike);
    }


    public boolean deleteCommentLike(ObjectId id) {
        return commentLikeRepository.findById(id).map(like -> {
        commentLikeRepository.delete(like);
        return true; 
        }).orElse(false);
    }


    
}
