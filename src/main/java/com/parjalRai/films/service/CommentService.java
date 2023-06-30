package com.parjalRai.films.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Comment;
import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.CommentRepository;
import com.parjalRai.films.repository.DiscussionRepository;
import com.parjalRai.films.repository.UserEntityRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Service
public class CommentService {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Comment> findParentComments() {
        Query query = new Query();
        query.addCriteria(Criteria.where("parentComment").is(null));

        return mongoTemplate.find(query, Comment.class);
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> findAllCommentsByUser(String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);

        UserEntity user = optUser.orElseThrow(() -> new NotFoundException(username + " not found"));
        return commentRepository.findByUser(user);
    }


    public List<Comment> findChildCommentsByDiscussionId(ObjectId discussionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("discussion").is(discussionId));
        query.addCriteria(Criteria.where("parentComment").ne(null));
        return mongoTemplate.find(query, Comment.class);
    }

    public List<Comment> findParentCommentsByDiscussionId(ObjectId discussionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("discussion").is(discussionId));
        query.addCriteria(Criteria.where("parentComment").is(null));
        return mongoTemplate.find(query, Comment.class); 
    }




    public List<Comment> findChildComments() {
        Query query = new Query();
        query.addCriteria(Criteria.where("parentComment").ne(null));
        return mongoTemplate.find(query, Comment.class);
    }


 
    public List<Comment> findAllCommentsByDiscussion(ObjectId discussionId) {
        Optional<Discussion> optDiscussion = discussionRepository.findById(discussionId);

        Discussion discussion = optDiscussion.orElseThrow(() -> new NotFoundException("discussion not found"));

        return commentRepository.findByDiscussion(discussion);
    }

  


    public Comment createComment(String text, ObjectId discussionId, ObjectId commentId, String username, Long likes) {
        Optional<Discussion> optDiscussion = discussionRepository.findById(discussionId);
        Optional<UserEntity> optUser = userRepository.findByUsername(username);

        Discussion discussion = optDiscussion.orElseThrow(() -> new NotFoundException("discussion not found"));
        UserEntity user = optUser.orElseThrow(() -> new NotFoundException(username + " not found"));
        System.err.println("comment id, " + commentId);
        System.err.println("discussion id. " + discussionId);
        Optional<Comment> optComment = commentRepository.findById(commentId);

        Comment comment = new Comment();
        comment.setDiscussion(discussion);
        comment.setParentComment(optComment.orElse(null));
        comment.setText(text);
        comment.setTimestamp(Instant.now());
        comment.setLikes(likes);
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    public boolean isTheOwner(ObjectId id, String username) {

        Optional<Comment> optComment = commentRepository.findById(id);
        Optional<UserEntity> optUser = userRepository.findByUsername(username);

        Comment comment = optComment.orElseThrow(() -> new NotFoundException("Comment not found"));
        UserEntity user = optUser.orElseThrow(() -> new NotFoundException("User not found"));

        return comment != null && comment.getUser().equals(user);
    }

    public boolean deleteComment(ObjectId id) {
        return commentRepository.findById(id).map(comment -> {
            commentRepository.delete(comment);
            return true;
        }).orElse(false);
    }

    public Comment updateCommentDetails(Comment comment, ObjectId id) {
        try {
            Optional<Comment> optComment = commentRepository.findById(id);
            Comment updatedComment = optComment.get();
            if (comment.getText() != null) {
                updatedComment.setText(comment.getText());
            }

            updatedComment.setLikes(comment.getLikes());
            updatedComment.setTimestamp(Instant.now());

            return commentRepository.save(updatedComment);

        } catch (Exception e) {
            System.err.println("Error while updating comment: " + e.getMessage());
        }

        return null;
    }

    

}
