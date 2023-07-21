package com.parjalRai.films.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.DiscussionLike;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.DiscussionLikeRepository;
import com.parjalRai.films.repository.DiscussionRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@Service
public class DiscussionLikeService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private DiscussionRepository discussionRepository;
    
    @Autowired
    private DiscussionLikeRepository discussionLikeRepository;
    

    public List<DiscussionLike> findByDiscussion(Discussion discussion) {
        return discussionLikeRepository.findByDiscussion(discussion);
    }

    public List<DiscussionLike> findByUserEntity(UserEntity user) {
        return discussionLikeRepository.findByUserEntity(user);
    }

    public DiscussionLike createDiscussionLike(ObjectId userId, ObjectId discussionId) {

        DiscussionLike discussionLike = new DiscussionLike();

        Optional<Discussion> optDiscussion = discussionRepository.findById(discussionId);
        Optional<UserEntity> optUser = userEntityRepository.findById(userId);

        Discussion discussion = optDiscussion.orElseThrow(() -> new NotFoundException("Discussion not found"));
        UserEntity user = optUser.orElseThrow(() -> new NotFoundException("User not found"));

        discussionLike.setDiscussion(discussion);
        discussionLike.setUserEntity(user);
        discussionLike.setTimestamp(Instant.now());

        return discussionLikeRepository.save(discussionLike);
    }
    
    public boolean deleteDiscussionLike(ObjectId id) {
        return discussionLikeRepository.findById(id).map(like->{
            discussionLikeRepository.delete(like);
            return true; 
        }).orElse(false);
    }
    
}
