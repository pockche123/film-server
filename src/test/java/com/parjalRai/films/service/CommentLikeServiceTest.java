package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parjalRai.films.model.Comment;
import com.parjalRai.films.model.CommentLike;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.CommentLikeRepository;
import com.parjalRai.films.repository.CommentRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@ExtendWith(MockitoExtension.class)
public class CommentLikeServiceTest {

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentLikeRepository commentLikeRepository;

    @InjectMocks
    private CommentLikeService commentLikeService;

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
    public void findByComment_ReturnAllCommentsLike_WhenCommentFound() {
        Comment comment = new Comment();
        List<CommentLike> expectedCommentLikes = Arrays.asList(commentLike1, commentLike2);
        when(commentLikeRepository.findByComment(comment)).thenReturn(expectedCommentLikes);

        List<CommentLike> actualCommentLikes = commentLikeService.findByComment(comment);

        assertEquals(expectedCommentLikes, actualCommentLikes);
        verify(commentLikeRepository).findByComment(comment);
    }

    @Test
    public void findByUserEntity_ReturnsAllCommentsLike_WhenCommentFound() {
        UserEntity user = new UserEntity();
        List<CommentLike> expectedCommentLikes = Arrays.asList(commentLike1, commentLike2);
        when(commentLikeRepository.findByUserEntity(user)).thenReturn(expectedCommentLikes);

        List<CommentLike> actualCommentLikes = commentLikeService.findByUserEntity(user);

        assertEquals(expectedCommentLikes, actualCommentLikes);
        verify(commentLikeRepository).findByUserEntity(user);

    }

    @Test
    public void createCommentLike_ReturnsACommentLike_WhenCreated() {
        ObjectId userId = new ObjectId();
        ObjectId commentId = new ObjectId();
        Comment comment = new Comment();
        UserEntity user = new UserEntity();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(userEntityRepository.findById(userId)).thenReturn(Optional.of(user));
        commentLike1.setComment(comment);
        commentLike1.setUserEntity(user);
        when(commentLikeRepository.save(ArgumentMatchers.any(CommentLike.class))).thenReturn(commentLike1);

        CommentLike actualCommentLike = commentLikeService.createCommentLike(userId, commentId);

        assertEquals(commentLike1, actualCommentLike);

    }

    @Test
    public void deleteCommentLike_ReturnTrue_WhenCommentLikeDeleted() {
        ObjectId id = new ObjectId();
        commentLike1.setId(id);
        when(commentLikeRepository.findById(id)).thenReturn(Optional.of(commentLike1));
        

        boolean result = commentLikeService.deleteCommentLike(id);

        assertTrue(result);
        verify(commentLikeRepository).delete(commentLike1);
    }


}
