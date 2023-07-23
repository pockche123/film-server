package com.parjalRai.films.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.DiscussionLike;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.DiscussionLikeRepository;
import com.parjalRai.films.repository.DiscussionRepository;
import com.parjalRai.films.repository.UserEntityRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class DiscussionLikeServiceTest {

    @Mock
    private UserEntityRepository userEntityRepository;
    
    @Mock
    private DiscussionRepository discussionRepository;
    
    @Mock
    private DiscussionLikeRepository discussionLikeRepository;
    
    @InjectMocks
    private DiscussionLikeService discussionLikeService;

    private DiscussionLike discussionLike1;
    private DiscussionLike discussionLike2; 
    
    @BeforeEach
    public void setUp() {
        discussionLike1 = new DiscussionLike();
        discussionLike2 = new DiscussionLike();
    }
    
    @AfterEach
    public void tearDown() {
        discussionLike1 = null;
        discussionLike2 = null;
    }
    
    @Test
    public void findByDiscussion_ReturnAllDiscussionsLike_WhenDiscussionFound() {
        Discussion Discussion = new Discussion();
        List<DiscussionLike> expectedDiscussionLikes = Arrays.asList(discussionLike1, discussionLike2);
        when(discussionLikeRepository.findByDiscussion(Discussion)).thenReturn(expectedDiscussionLikes);

        List<DiscussionLike> actualDiscussionLikes = discussionLikeService.findByDiscussion(Discussion);

        assertEquals(expectedDiscussionLikes, actualDiscussionLikes);
        verify(discussionLikeRepository).findByDiscussion(Discussion);
    }



    @Test
    public void findByUserEntity_ReturnsAllDiscussionsLike_WhenDiscussionFound() {
        UserEntity user = new UserEntity();
        List<DiscussionLike> expectedDiscussionLikes = Arrays.asList(discussionLike1, discussionLike2);
        when(discussionLikeRepository.findByUserEntity(user)).thenReturn(expectedDiscussionLikes);

        List<DiscussionLike> actualDiscussionLikes = discussionLikeService.findByUserEntity(user);

        assertEquals(expectedDiscussionLikes, actualDiscussionLikes);
        verify(discussionLikeRepository).findByUserEntity(user);

    }

    @Test
    public void createDiscussionLike_ReturnsADiscussionLike_WhenCreated() {
        ObjectId userId = new ObjectId();
        ObjectId DiscussionId = new ObjectId();
        Discussion Discussion = new Discussion();
        UserEntity user = new UserEntity();
        when(discussionRepository.findById(DiscussionId)).thenReturn(Optional.of(Discussion));
        when(userEntityRepository.findById(userId)).thenReturn(Optional.of(user));
        discussionLike1.setDiscussion(Discussion);
        discussionLike1.setUserEntity(user);
        when(discussionLikeRepository.save(ArgumentMatchers.any(DiscussionLike.class))).thenReturn(discussionLike1);

        DiscussionLike actualDiscussionLike = discussionLikeService.createDiscussionLike(userId, DiscussionId);

        assertEquals(discussionLike1, actualDiscussionLike);

    }


    @Test
    public void deletediscussionLike_ReturnTrue_WhendiscussionLikeDeleted() {
        ObjectId id = new ObjectId();
        discussionLike1.setId(id);
        when(discussionLikeRepository.findById(id)).thenReturn(Optional.of(discussionLike1));

        boolean result = discussionLikeService.deleteDiscussionLike(id);

        assertTrue(result);
        verify(discussionLikeRepository).delete(discussionLike1);
    }




}
