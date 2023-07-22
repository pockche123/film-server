package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parjalRai.films.model.Comment;
import com.parjalRai.films.model.CommentLike;
import com.parjalRai.films.repository.CommentLikeRepository;
import com.parjalRai.films.repository.CommentRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@ExtendWith(MockitoExtension.class)
public class CommentLikeControllerTest {
    
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



    


}
