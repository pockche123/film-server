package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.Watched;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.repository.WatchedRepository;

@ExtendWith(MockitoExtension.class)
public class WatchedServiceTest {
    

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private WatchedRepository watchedRepository;
    
    @InjectMocks
    private WatchedService watchedService;
    

    private Watched watched1;

    @BeforeEach
    public void setUp() {
        watched1 = new Watched();
    }

    @AfterEach
    public void tearDown() {
        watched1 = null;
    }


    @Test
    public void testFindWatchedOfAUser_ReturnAListOfUser_WhenUserFound() {
        UserEntity user1 = new UserEntity();
        List<Watched> expectedList = Arrays.asList(watched1);

        when(watchedRepository.findByUserEntity(user1)).thenReturn(expectedList);

        List<Watched> actualWatched = watchedService.findWatchedOfAUser(user1);

        assertNotNull(actualWatched);
        assertEquals(expectedList, actualWatched);
        verify(watchedRepository).findByUserEntity(user1);

    }
    

    @Test
    public void createWatched_WithValidDate_ShouldCreateWatched() {
        Film film = new Film();
        String title = "django";
        UserEntity user1 = new UserEntity();
        String username = "bigman";

        Watched watched = new Watched();
        watched.setFilm(film);
        watched.setUserEntity(user1);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(user1));
        when(filmRepository.findFilmByTitleIgnoreCase(title)).thenReturn(Optional.of(film));

        when(watchedRepository.save(ArgumentMatchers.any(Watched.class))).thenReturn(watched);

        Watched actualWatched = watchedService.createWatched(username, title);

        assertNotNull(actualWatched);
        assertEquals(watched, actualWatched);
        verify(watchedRepository).save(any());
    }

    @Test
    public void deleteWatched_WhenWatchedExists_ShouldReturnTrue() {

        ObjectId id = new ObjectId();
        when(watchedRepository.findById(id)).thenReturn(Optional.of(watched1));

        boolean result = watchedService.deleteWatched(id);

        assertTrue(result);
        verify(watchedRepository).delete(watched1);
    }
    


}
