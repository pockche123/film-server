package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
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

import com.parjalRai.films.model.Favourite;
import com.parjalRai.films.model.dto.FavouriteDTO;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.FavouriteService;

@ExtendWith(MockitoExtension.class)
public class FavouriteControllerTest {
    

    @Mock
    private FavouriteService favouriteService;

    @Mock
    private UserEntityRepository userRepository; 
    
    @InjectMocks
    private FavouriteController favouriteController;

    private Favourite favourite1, favourite2; 

    @BeforeEach 
    void buildUp() {
        favourite1 = new Favourite();
        favourite2 = new Favourite();
    }
    
    @AfterEach
    void tearDown() {
        favourite1 = null;
        favourite2 = null;
        
    }


    @Test
    void test_getAllFavourites_returnsAListOFFavourites() {

        List<Favourite> expected = Arrays.asList(favourite1, favourite2);
        when(favouriteService.findAllFavorites()).thenReturn(expected);

        ResponseEntity<List<Favourite>> response = favouriteController.getAllFavourites();

        assertNotNull(response);
        assertEquals(expected, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(favouriteService).findAllFavorites();

    }
    

    @Test
    void test_getAllFavourites_returnsAListOfFavourites_whenValidUsernameFound() {

        String username = "django";
        List<Favourite> expected = Arrays.asList(favourite1, favourite2);
        when(favouriteService.findAllFavouritesByUsername(username)).thenReturn(expected);

        ResponseEntity<List<Favourite>> response = favouriteController.getAllFavouritesByUsername(username);

        assertNotNull(response);
        assertEquals(expected, response.getBody());
        verify(favouriteService).findAllFavouritesByUsername(username);

    }
    
    @Test 
    void test_createAFavouriteObject_returnsOkStatus_WhenFavouriteCreated() {

        String username = "django";
        String filmTitle = "unchained";
        FavouriteDTO favouriteDto = new FavouriteDTO(username, filmTitle);
        when(favouriteService.createFavourite(username, filmTitle)).thenReturn(favourite1);

        ResponseEntity<Favourite> response = favouriteController.createFavourites(favouriteDto);

        assertNotNull(response);
        assertEquals(favourite1, response.getBody());
        verify(favouriteService).createFavourite(username, filmTitle);

    }
    
    @Test
    void test_deleteFavouriteObject_returnsOkStatus_whenDeleted() {
        ObjectId id = new ObjectId();

        when(favouriteService.deleteFavourite(id)).thenReturn(true);
        
        ResponseEntity<String> response = favouriteController.deleteFavouriteObject(id);
        
        assertNotNull(response);
        assertEquals("Favourite Object deleted successfully.", response.getBody());
        verify(favouriteService).deleteFavourite(id);

    }
    
    
    
}
