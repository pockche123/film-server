package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


import java.util.Arrays;
import java.util.List;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.dto.DiscussionDTO;
import com.parjalRai.films.service.DiscussionService;

@ExtendWith(MockitoExtension.class)
public class DiscussionControllerTests {


    @Mock
    private DiscussionService discussionService;
    
    @InjectMocks
    private DiscussionController discussionController;

    private Discussion discussion1;
    private Discussion discussion2;

    @BeforeEach
    public void setUp()  {
        discussion1 = new Discussion();
        discussion2 = new Discussion();
    }
    
    @AfterEach
    public void tearDown() {
        discussion1 = null;
        discussion2 = null;
    }

    @Test
    void getAllDiscussion_returnListOfDiscussions() {
        //arrange
        List<Discussion> expected = Arrays.asList(discussion1, discussion2);
        when(discussionService.getAllDiscussions()).thenReturn(expected);

        //act
        ResponseEntity<List<Discussion>> response = discussionController.getAllDiscussions();

        //assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(discussionService).getAllDiscussions();
    }

    @Test
    void getDiscussionsByFilm_ReturnListOfDiscussions() {
        //arrange
        List<Discussion> expected = Arrays.asList(discussion1, discussion2);
        String title = "Django";
        when(discussionService.getDiscussionsByFilm(title)).thenReturn(expected);

        //act
        ResponseEntity<List<Discussion>> response = discussionController.getDiscussionsByFilm(title);

        //assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(discussionService).getDiscussionsByFilm(title);
    }



    @Test
    public void testDeleteDiscussion_WhenOwnerAndDiscussionExists_ReturnsOk() {
        ObjectId discussionId = new ObjectId();
        String username = "testUser";

        // Create a mock Authentication object
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);

        // Create a mock SecurityContext
        SecurityContext securityContext = mock(SecurityContext.class);

        // Set the SecurityContext in the SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);

        when(discussionService.isTheOwner(discussionId, username)).thenReturn(true);
        when(discussionService.deleteDiscussion(discussionId)).thenReturn(true);

        ResponseEntity<String> response = discussionController.deleteDiscussion(discussionId, authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Discussion deleted successfully", response.getBody());
    }

    @Test
    void getDiscussionsByUser_ReturnListOfDiscussions_ifUsernamefound() {
        List<Discussion> expected = Arrays.asList(discussion1, discussion2);
        String username = "user1";
        when(discussionService.getDiscussionsByUser(username)).thenReturn(expected);

        ResponseEntity<List<Discussion>> response = discussionController.getDiscussionsByUser(username);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(discussionService).getDiscussionsByUser(username);

    }
    
    @Test
    void createDiscussion_WhenAllDetailsAreValid() {
        //arrange

        DiscussionDTO discussionDTO = new DiscussionDTO("descirption", "title", "user1", "title2", 5l);
        Discussion createdDiscussion = new Discussion();
        createdDiscussion.setDescription(discussionDTO.description());

        when(discussionService.createDiscussion(discussionDTO.filmTitle(),
                discussionDTO.username(), discussionDTO.title(), discussionDTO.description(),
                discussionDTO.likes())).thenReturn(createdDiscussion);

        ResponseEntity<Discussion> response = discussionController.createDiscussion(discussionDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdDiscussion, response.getBody());
        verify(discussionService).createDiscussion(discussionDTO.filmTitle(),
                discussionDTO.username(), discussionDTO.title(), discussionDTO.description(),
                discussionDTO.likes());

    }
    

    @Test
    public void testUpdateDiscussion_WhenOwnerAndDiscussionExists_ReturnsOk() {
        ObjectId discussionId = new ObjectId();
        Discussion discussion = new Discussion();
        discussion.setId(discussionId);

        String username = "testUser";
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);

        Discussion updatedDiscussion = new Discussion();
        when(discussionService.updateDiscussionDetails(discussion, discussionId)).thenReturn(updatedDiscussion);
        when(discussionService.isTheOwner(discussionId, username)).thenReturn(true);

        ResponseEntity<Discussion> response = discussionController.updateDiscussion(discussion, discussionId,
                authentication);

        verify(discussionService, times(1)).updateDiscussionDetails(discussion, discussionId);
        verify(discussionService, times(1)).isTheOwner(discussionId, username);
        verifyNoMoreInteractions(discussionService);

        verify(authentication, times(1)).getName();
        verifyNoMoreInteractions(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDiscussion, response.getBody());
    }

}
