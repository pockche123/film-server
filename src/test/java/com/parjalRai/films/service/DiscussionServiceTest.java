package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.DiscussionRepository;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@ExtendWith(MockitoExtension.class)
public class DiscussionServiceTest {


    @Mock
    private DiscussionRepository discussionRepository;
    
    @Mock
    private FilmRepository filmRepository;

    @Mock
    private UserEntityRepository userRepository;
    
    @InjectMocks
    private DiscussionService discussionService;
    
    private Discussion discussion1;
    private Discussion discussion2;
    
    @BeforeEach
    public void setup() {
        discussion1 = new Discussion();
        discussion2 = new Discussion();
    }
    
    @AfterEach
    public void tearDown() {
        discussion1 = null;
        discussion2 = null;
    }


    @Test
    void getAllDiscussions_ReturnsAListOfDisucssions() {
        //Arrange
        List<Discussion> expectedDiscussion = Arrays.asList(discussion1, discussion2);
        when(discussionRepository.findAll()).thenReturn(expectedDiscussion);

        //Act
        List<Discussion> actualDiscussion = discussionService.getAllDiscussions();

        //Assert
        assertEquals(expectedDiscussion, actualDiscussion);
        verify(discussionRepository).findAll();
    }

    @Test
    void getDiscussionsByFilm_ReturnAListOfDiscussions_WhenValidFilmTitlePassed() {
        //Arrange
        Film film = new Film();
        String title = "Django";
        film.setTitle(title);
        List<Discussion> expectedDiscussion = Arrays.asList(discussion1, discussion2);
        when(filmRepository.findFilmByTitleIgnoreCase(title)).thenReturn(Optional.of(film));
        when(discussionRepository.findByFilm(film)).thenReturn(expectedDiscussion);

        //Act
        List<Discussion> actualDiscussion = discussionService.getDiscussionsByFilm(title);

        //Assert
        assertEquals(expectedDiscussion, actualDiscussion);
        verify(discussionRepository).findByFilm(film);

    }

    @Test
    void getDiscussionsByUser_ReturnAListOfDiscussions_WhenValidUsernamePassed() {
        //Arrange
        UserEntity user = new UserEntity();
        String username = "user1";
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        List<Discussion> expectedDiscussion = Arrays.asList(discussion1, discussion2);
        when(discussionRepository.findByUser(user)).thenReturn(expectedDiscussion);

        // Act
        List<Discussion> actualDiscussion = discussionService.getDiscussionsByUser(username);

        // Assert
        assertEquals(expectedDiscussion, actualDiscussion);
        verify(discussionRepository).findByUser(user);
    }
    
    @Test
    void createDiscussion_WhenDiscussionDetailsProvided() {
        
         Film film = new Film();
        String title = "Django";
        film.setTitle(title);
        when(filmRepository.findFilmByTitleIgnoreCase(title)).thenReturn(Optional.of(film));

        UserEntity user = new UserEntity();
        String username = "user1";
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        String description = "A new discussion.";
        Long likes = (long) 5; 

        Discussion discussion = new Discussion();
        discussion.setFilm(film);
        discussion.setUser(user);
        discussion.setTitle(title);
        discussion.setTimestamp(Instant.now());
        discussion.setDescription(description);
        discussion.setLikes(likes);

        when(discussionRepository.save(ArgumentMatchers.any(Discussion.class))).thenReturn(discussion);

        //Act
        Discussion actualDiscussion = discussionService.createDiscussion(title, username, title, description, likes);

        //Assert
        assertEquals(discussion, actualDiscussion);
        verify(discussionRepository).save(any());




    }

    
}
