package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.parjalRai.films.model.Comment;
import com.parjalRai.films.model.dto.CommentDTO;
import com.parjalRai.films.service.CommentService;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Mock
    private CommentService commentService; 

    @InjectMocks
    private CommentController commentController;

    private Comment comment1;
    private Comment comment2;
    
    @BeforeEach
    public void setUp()  {
        comment1 = new Comment();
        comment2 = new Comment();
    }
    
    
    @AfterEach
    public void tearDown()  {
        comment1 = null;
        comment2 = null;
    }
    
    
    @Test
    void getAllComments_ReturnsListOfComments() {
        //Arrange
        List<Comment> comments = Arrays.asList(comment1, comment2);
        when(commentService.findAllComments()).thenReturn(comments);

        //Act
        ResponseEntity<List<Comment>> response = commentController.getAllComments();

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comments, response.getBody());
        verify(commentService).findAllComments();
    }

    @Test
    void getParentComments_ReturnsListOfComments() {
        // arrange
        List<Comment> parentComments = Arrays.asList(comment1, comment2);
        when(commentService.findParentComments()).thenReturn(parentComments);

        // act
        ResponseEntity<List<Comment>> responseEntity = commentController.getAllParentComments();

        // assert
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() != null;
        assert responseEntity.getBody().size() == parentComments.size();
        assert responseEntity.getBody().containsAll(parentComments);
        verify(commentService).findParentComments();
    }

    @Test
    void getChildComments_ReturnsListOfComments() {
        //arrange
        comment1.setParentComment(comment2);
        List<Comment> childComments = Arrays.asList(comment1);
        when(commentService.findChildComments()).thenReturn(childComments);

        //act
        ResponseEntity<List<Comment>> responseEntity = commentController.getAllChildComments();

        //assert
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() != null;
        assert responseEntity.getBody().size() == childComments.size();
        assert responseEntity.getBody().containsAll(childComments);
        verify(commentService).findChildComments();

    }



    @Test
    void getAllCommentsByUser_ReturnsListOfComments_WhenValidUsernamePassedIn() {
        //Arrange
        List<Comment> comments = Arrays.asList(comment1, comment2);
        String username = "user1";
        when(commentService.findAllCommentsByUser(username)).thenReturn(comments);

        //Act
        ResponseEntity<List<Comment>> response = commentController.getAllCommmentsByUser(username);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comments, response.getBody());
        verify(commentService).findAllCommentsByUser(username);

    }

    @Test
    void getAllCommentsByDiscussion_ReturnsListOfComments_WhenValidDisussionIdPassed() {
        //Assert
        List<Comment> comments = Arrays.asList(comment1, comment2);
        ObjectId discussionId = new ObjectId();
        when(commentService.findAllCommentsByDiscussion(discussionId)).thenReturn(comments);

        //Act
        ResponseEntity<List<Comment>> response = commentController.getAllCommentsByDiscussion(discussionId);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comments, response.getBody());
        verify(commentService).findAllCommentsByDiscussion(discussionId);
    }
    

    @Test
    void createAComment_whenAllValidetailsProvided() {

        //Arrange
        ObjectId discussId = new ObjectId();
        ObjectId commentId = new ObjectId();
        Long likes = (long) 5;
        CommentDTO commentDTO = new CommentDTO(commentId, discussId, "text", "user1", likes);
        Comment createdComment = new Comment();
        createdComment.setId(new ObjectId());
        createdComment.setLikes(commentDTO.likes());

        when(commentService.createComment(commentDTO.text(), commentDTO.discussionId(), commentDTO.commentId(),
                commentDTO.username(), commentDTO.likes())).thenReturn(createdComment);
        //Act
        ResponseEntity<Comment> response = commentController.createComment(commentDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdComment, response.getBody());
        verify(commentService).createComment(commentDTO.text(), commentDTO.discussionId(),
                commentDTO.commentId(), commentDTO.username(), commentDTO.likes());

    }

    // @Test
    // void deleteComment_WhenCommentDoesExist_ReturnOKResponse() {
    //     //Arrange
    //     ObjectId id = new ObjectId();
    //     when(commentService.deleteComment(id)).thenReturn(true);

    //     //Act
    //     ResponseEntity<String> response = commentController.deleteComment(id);

    //     //Assert
    //     assertNotNull(response);
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals("Comment deleted successfully", response.getBody());
    //     verify(commentService).deleteComment(id);
    // }

    // @Test
    // void updateComment_WhenCommentUpdated_houldReturnOkResponse(){
    //     //Arrange
    //     ObjectId id = new ObjectId();
    //     Comment updatedComment = new Comment();
    //     updatedComment.setId(id);
    //     updatedComment.setLikes(7);
        
    //     when(commentService.updateCommentDetails(updatedComment, id)).thenReturn(updatedComment);

    //     //Act
    //     ResponseEntity<Comment> response = commentController.updateComment(updatedComment, id);

    //             // Assert
    //     assertNotNull(response);
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals(updatedComment, response.getBody());
    //     verify(commentService).updateCommentDetails(updatedComment, id);
        
    // }

    

    
}
