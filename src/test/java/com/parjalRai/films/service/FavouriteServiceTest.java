package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
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

import com.parjalRai.films.model.Favourite;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.FavouriteRepository;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@ExtendWith(MockitoExtension.class)
public class FavouriteServiceTest {

    @Mock
    private FavouriteRepository favouriteRepository;

    @Mock 
    private UserEntityRepository userRepository;
    
    @Mock 
    private FilmRepository filmRepository;
    
    @InjectMocks
    private FavouriteService favouriteService;
    
    private Favourite favourite1, favourite2;
    
    @BeforeEach
    public void setUp() {
        favourite1 = new Favourite();
        favourite2 = new Favourite();
    }
    

    @AfterEach
    public void tearDown() {
        favourite1 = null;
        favourite2 = null;
    }
    
    @Test
    public void test_findAllFavourites_ReturnsAListOfFavourites() {
        //arrange
        List<Favourite> expected = Arrays.asList(favourite1, favourite2);
        when(favouriteRepository.findAll()).thenReturn(expected);

        //act
        List<Favourite> actual = favouriteService.findAllFavorites();
        //assert
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(favouriteRepository).findAll();

    }
    
    @Test
    public void test_findAllFavouritesByUsername_ReturnsAListOfFavourites_WhenValidUsernamePassed() {
        //arrange
        List<Favourite> expected = Arrays.asList(favourite1, favourite2);
        String username = "django";
        UserEntity user1 = new UserEntity();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user1));
        when(favouriteRepository.findByUserEntity(user1)).thenReturn(expected);

        //act
        List<Favourite> actual = favouriteService.findAllFavouritesByUsername(username);

        //assert
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(favouriteRepository).findByUserEntity(user1);

    }

    @Test
    public void test_createFavouriteObject_WhenValidUsernameAndFilmTitlePassed() {
        //arrange
        String username = "django";
        UserEntity user1 = new UserEntity();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user1));
        String filmTitle = "unchained";
        Film film = new Film();
        when(filmRepository.findFilmByTitleIgnoreCase(filmTitle)).thenReturn(Optional.of(film));
        Favourite expectedFavourite = new Favourite();
        expectedFavourite.setFilm(film);
        expectedFavourite.setUserEntity(user1);
        when(favouriteRepository.save(ArgumentMatchers.any())).thenReturn(expectedFavourite);

        //act
        Favourite actualFavourite = favouriteService.createFavourite(username, filmTitle);

        assertNotNull(actualFavourite);
        assertEquals(expectedFavourite, actualFavourite);
        verify(favouriteRepository).save(expectedFavourite);
    }

    @Test
    public void deleteFavourite_returnTrue_WhenValidObjectIdPassed() {

        ObjectId id = new ObjectId();
        when(favouriteRepository.findById(id)).thenReturn(Optional.of(favourite1));

        Boolean deleted = favouriteService.deleteFavourite(id);

        assertTrue(deleted);
        verify(favouriteRepository).delete(favourite1);

    }
    
    @Test
    public void deleteFavourite_returnTrue_WhenInvalidObjectIdPassed() {

        ObjectId id = new ObjectId();
        when(favouriteRepository.findById(id)).thenReturn(Optional.empty());

        Boolean deleted = favouriteService.deleteFavourite(id);

        assertFalse(deleted);
         verify(favouriteRepository, never()).delete(favourite1);

    }


    
}
