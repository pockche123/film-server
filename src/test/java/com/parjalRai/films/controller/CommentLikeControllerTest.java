package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.parjalRai.films.model.Comment;
import com.parjalRai.films.model.CommentLike;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.dto.CommentLikeDTO;
import com.parjalRai.films.repository.CommentRepository;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.CommentLikeService;


@ExtendWith(MockitoExtension.class)
public class CommentLikeControllerTest {

    @Mock
    private CommentLikeService commentLikeService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserEntityRepository userEntityRepository;

    @InjectMocks
    private CommentLikeController commentLikeController;

    private CommentLike commentLike1;
    private CommentLike commentLike2;

    @BeforeEach
    public void setUp() {
        commentLike1 = new CommentLike();
        commentLike2 = new CommentLike();
    }

    @AfterEach
    public void tearDown() {
        commentLike1 = null;
        commentLike2 = null;
    }

    @Test
    public void getAllLikesByComment_ReturnsOkStatus_WhenAllCommentLikeReturns() {
        ObjectId id = new ObjectId();
        Comment Comment = new Comment();
        when(commentRepository.findById(id)).thenReturn(Optional.of(Comment));
        List<CommentLike> expected = Arrays.asList(commentLike1, commentLike2);
        when(commentLikeService.findByComment(Comment)).thenReturn(expected);

        ResponseEntity<List<CommentLike>> response = commentLikeController.getAllLikesByComment(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(commentLikeService).findByComment(Comment);

    }

    @Test
    public void getAllLikesByUserEntity_ReturnsOkStatus_WhenAllCommentLikeReturns() {
        ObjectId id = new ObjectId();
        UserEntity user = new UserEntity();
        when(userEntityRepository.findById(id)).thenReturn(Optional.of(user));
        List<CommentLike> expected = Arrays.asList(commentLike1, commentLike1);
        when(commentLikeService.findByUserEntity(user)).thenReturn(expected);

        ResponseEntity<List<CommentLike>> response = commentLikeController.getAllLikesByUserEntity(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(commentLikeService).findByUserEntity(user);

    }

    @Test
    public void createCommentLike_ReturnsOkStatus_WhenCommentLikeCreated() {
        ObjectId userId = new ObjectId();
        ObjectId CommentId = new ObjectId();

        CommentLikeDTO CommentLikeDTO = new CommentLikeDTO(userId, CommentId);
        CommentLike createdCommentLike = new CommentLike();
        UserEntity user = new UserEntity();
        createdCommentLike.setUserEntity(user);
        when(commentLikeService.createCommentLike(userId, CommentId)).thenReturn(createdCommentLike);

        ResponseEntity<CommentLike> response = commentLikeController.createCommentLike(CommentLikeDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdCommentLike, response.getBody());
        verify(commentLikeService).createCommentLike(userId, CommentId);

    }

    @Test
    public void testDeletecommentLike_ReturnsOkStatus_WhenDeleted() {
        ObjectId id = new ObjectId();
        when(commentLikeService.deleteCommentLike(id)).thenReturn(true);

        ResponseEntity<String> response = commentLikeController.deletecommentLike(id);

        assertNotNull(response);
        assertEquals("Comment like deleted successfully.", response.getBody());
        verify(commentLikeService).deleteCommentLike(id);

    }
    
    
}
