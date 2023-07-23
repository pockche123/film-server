package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.DiscussionLike;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.dto.DiscussionLikeDTO;
import com.parjalRai.films.repository.DiscussionRepository;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.DiscussionLikeService;

@ExtendWith(MockitoExtension.class)
public class DiscussionLikeControllerTest {

    @Mock
    private DiscussionLikeService discussionLikeService;
    
    @Mock
    private DiscussionRepository discussionRepository;
    
    @Mock
    private UserEntityRepository userEntityRepository;
    
    @InjectMocks
    private DiscussionLikeController discussionLikeController;

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
    public void getAllLikesByDiscussion_ReturnsOkStatus_WhenAllDiscussionLikeReturns() {
        ObjectId id = new ObjectId();
        Discussion discussion = new Discussion();
        when(discussionRepository.findById(id)).thenReturn(Optional.of(discussion));
        List<DiscussionLike> expected = Arrays.asList(discussionLike1, discussionLike2);
        when(discussionLikeService.findByDiscussion(discussion)).thenReturn(expected);

        ResponseEntity<List<DiscussionLike>> response = discussionLikeController.getAllLikesByDiscussion(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(discussionLikeService).findByDiscussion(discussion);

    }

    @Test
    public void getAllLikesByUserEntity_ReturnsOkStatus_WhenAllDiscussionLikeReturns() {
        ObjectId id = new ObjectId();
        UserEntity user = new UserEntity();
        when(userEntityRepository.findById(id)).thenReturn(Optional.of(user));
        List<DiscussionLike> expected = Arrays.asList(discussionLike1, discussionLike1);
        when(discussionLikeService.findByUserEntity(user)).thenReturn(expected);

        ResponseEntity<List<DiscussionLike>> response = discussionLikeController.getAllLikesByUserEntity(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(discussionLikeService).findByUserEntity(user);

    }

    @Test 
    public void createDiscussionLike_ReturnsOkStatus_WhenDiscussionLikeCreated() {
        ObjectId userId = new ObjectId();
        ObjectId discussionId = new ObjectId();

        DiscussionLikeDTO discussionLikeDTO = new DiscussionLikeDTO(userId, discussionId);
        DiscussionLike createdDiscussionLike = new DiscussionLike();
        UserEntity user = new UserEntity();
        createdDiscussionLike.setUserEntity(user);
        when(discussionLikeService.createDiscussionLike(userId, discussionId)).thenReturn(createdDiscussionLike);

        ResponseEntity<DiscussionLike> response = discussionLikeController.createDiscussionLike(discussionLikeDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdDiscussionLike, response.getBody());
        verify(discussionLikeService).createDiscussionLike(userId, discussionId);

    }
    
    @Test 
    public void testDeleteDiscussionLike_ReturnsOkStatus_WhenDeleted() {
        ObjectId id = new ObjectId();
        when(discussionLikeService.deleteDiscussionLike(id)).thenReturn(true);
        
        ResponseEntity<String> response = discussionLikeController.deleteDiscussionLike(id);

        assertNotNull(response);
        assertEquals("Discussion like deleted successfully.", response.getBody());
        verify(discussionLikeService).deleteDiscussionLike(id);

    }

    
    



}
