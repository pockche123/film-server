package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.parjalRai.films.model.WatchList;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.repository.WatchListRepository;

@ExtendWith(MockitoExtension.class)
public class WatchListServiceTest {
    
    @Mock
    private FilmRepository filmRepository;
    
    @Mock
    private UserEntityRepository userEntityRepository;
    
    @Mock
    private WatchListRepository watchListRepository;

    @InjectMocks
    private WatchListService watchListService; 

    private WatchList watchList1; 
    

    @BeforeEach
    public void setUp() {
        watchList1 = new WatchList();
    }

    @AfterEach
    public void tearDown() {
        watchList1 = null;
    }
    

    @Test
    public void testFindWatchListOfAUser_ReturnAListOfUser_WhenUserFound() {
        UserEntity user1 = new UserEntity();
        List<WatchList> expectedList = Arrays.asList(watchList1);

        when(watchListRepository.findByUserEntity(user1)).thenReturn(expectedList);

        List<WatchList> actualWatchList = watchListService.findWatchListOfAUser(user1);

        assertNotNull(actualWatchList);
        assertEquals(expectedList, actualWatchList);
        verify(watchListRepository).findByUserEntity(user1);

    }
    
    @Test
    public void createWatchList_WithValidDate_ShouldCreateWatchList() {
        Film film = new Film();
        String title = "django";
        UserEntity user1 = new UserEntity();
        String username = "bigman";

        WatchList watchList = new WatchList();
        watchList.setFilm(film);
        watchList.setUserEntity(user1);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(user1));
        when(filmRepository.findFilmByTitleIgnoreCase(title)).thenReturn(Optional.of(film));

        when(watchListRepository.save(ArgumentMatchers.any(WatchList.class))).thenReturn(watchList);

        WatchList actualWatchList = watchListService.createWatchList(username, title);

        assertNotNull(actualWatchList);
        assertEquals(watchList, actualWatchList);
        verify(watchListRepository).save(any());
    }

    
    @Test
    public void deleteWatchList_WhenWatchListExists_ShouldReturnTrue(){

        ObjectId id = new ObjectId(); 
        when(watchListRepository.findById(id)).thenReturn(Optional.of(watchList1)); 

        boolean result = watchListService.deleteWatchList(id); 

        assertTrue(result);
        verify(watchListRepository).delete(watchList1);
    }

}
