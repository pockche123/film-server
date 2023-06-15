package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
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
import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.CommentRepository;
import com.parjalRai.films.repository.DiscussionRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserEntityRepository userRepository;
    
    @Mock
    private DiscussionRepository discussionRepository;
    
    @InjectMocks
    private CommentService commentService;

    private Comment comment1;
    private Comment comment2;

    @BeforeEach
    public void setUp() {
        comment1 = new Comment();
        comment2 = new Comment();
    }

    @AfterEach
    public void tearDown() {
        comment1 = null;
        comment2 = null;
    }
    

    @Test
    void findAllComments_ReturnsListOfComments() {
        //Arrange
        List<Comment> expectedComments = Arrays.asList(comment1, comment2);
        when(commentRepository.findAll()).thenReturn(expectedComments);

        //Act
        List<Comment> actualComments = commentService.findAllComments();

        //Assert
        assertEquals(expectedComments.get(0), actualComments.get(0));
        assertEquals(expectedComments.get(0), actualComments.get(0));

        //verify 
        verify(commentRepository).findAll();
    }
    
    @Test
    void findAllCommentsByUser_ReturnsListOfComments_IfUserFound() {
        //Arrange
        UserEntity user = new UserEntity();
        user.setUsername("user1");
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        List<Comment> expectedComments = Arrays.asList(comment1, comment2);
        when(commentRepository.findByUser(user)).thenReturn(expectedComments);

        //Act
        List<Comment> actualComments = commentService.findAllCommentsByUser("user1");

        //Assert
        assertEquals(expectedComments.get(0), actualComments.get(0));
        assertEquals(expectedComments.get(1), actualComments.get(1));

        //verify
        verify(commentRepository).findByUser(user);
    }

    @Test
    void findAllCommentsByDiscussion_ReturnsAListOfComments_IfDiscussionFound() {
        //Arrange
        Discussion discussion = new Discussion();
        ObjectId id = new ObjectId();
        discussion.setId(id);
        when(discussionRepository.findById(id)).thenReturn(Optional.of(discussion));
        List<Comment> expectedComments = Arrays.asList(comment1, comment2);
        when(commentRepository.findByDiscussion(discussion)).thenReturn(expectedComments);

        //Act
        List<Comment> actualComments = commentService.findAllCommentsByDiscussion(id);

        //Assert
        assertEquals(expectedComments.get(0), actualComments.get(0));
        assertEquals(expectedComments.get(1), actualComments.get(1));

        //verify 
        verify(commentRepository).findByDiscussion(discussion);
    }

    @Test
    void createAComment_WhenAllCommentDetailsProvided() {
        //Arrange
        String text = "Good film.";
        ObjectId commentId = new ObjectId();
        ObjectId discussionId = new ObjectId();
        String username = "user1";
        Long likes = (long) 5;
        Comment parentComment = comment1;
        parentComment.setId(commentId);
        Discussion discussion = new Discussion();
        discussion.setId(discussionId);
        UserEntity user = new UserEntity();
        when(discussionRepository.findById(discussionId)).thenReturn(Optional.of(discussion));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(parentComment));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Comment comment = new Comment();
        comment.setDiscussion(discussion);
        comment.setLikes(likes);
        comment.setParentComment(parentComment);
        comment.setUser(user);
        comment.setTimestamp(Instant.now());
        comment.setText(text);

        //  when(commentRepository.save(comment)).thenReturn(comment);
        when(commentRepository.save(ArgumentMatchers.any(Comment.class))).thenReturn(comment);

        //Act
        Comment actualComment = commentService.createComment(text, discussionId, commentId, username, likes);

        //Assert
        assertEquals(comment, actualComment);
        verify(commentRepository).save(any());
    }

    @Test
    void deleteComment_ReturnTrue_whenCommentFound() {
        //Arrange
        ObjectId id = new ObjectId();
        comment1.setId(id);
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment1));

        //Act
        boolean result = commentService.deleteComment(id);

        //Assert
        assertTrue(result);
        verify(commentRepository).delete(comment1);

    }
    
    @Test 
    void deleteComment_ReturnFalse_whenCommentNotFound() {
        //Arrange
        ObjectId id = new ObjectId();
        when(commentRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        boolean result = commentService.deleteComment(id);

        //Assert
        assertFalse(result);
        verify(commentRepository, never()).delete(any());
    }
    
    @Test 
    void updateCommentDetails_ReturnsUpdatedComment_WhenCommentExists() {
        //Arrange
        ObjectId id = new ObjectId();
        comment1.setId(id);
        comment1.setLikes(5);
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment1));

        Comment updatedComment = new Comment();
        updatedComment.setLikes(10);
        when(commentRepository.save(comment1)).thenReturn(updatedComment);

        //Act
        Comment comment = commentService.updateCommentDetails(updatedComment, id);

        //Assert
        assertNotNull(comment);
        assertEquals(10, comment.getLikes());
        verify(commentRepository).findById(id);
        verify(commentRepository).save(comment1);

    }
    
}
