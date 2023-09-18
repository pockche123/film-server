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

import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.Watched;
import com.parjalRai.films.model.dto.WatchedDTO;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.WatchedService;

@ExtendWith(MockitoExtension.class)
public class WatchedControllerTest {
    


    @Mock
    private WatchedService watchedService;

    
    @Mock
    private UserEntityRepository userRepository;

    @InjectMocks
    private WatchedController watchedController;

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
    public void test_getUserwatched_withValidUsername_ReturnsAListOfwatched() {
        String username = "bigman";
        UserEntity user = new UserEntity();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        List<Watched> expectedList = Arrays.asList(watched1);
        when(watchedService.findWatchedOfAUser(user)).thenReturn(expectedList);

        ResponseEntity<List<Watched>> response = watchedController.getUserWatched(username);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
        verify(watchedService).findWatchedOfAUser(user);
    }


    @Test
    public void test_createWatched_ReturnsOkStatus_WhenWatchedCreated() {
        String username = "user1";
        String filmTitle = "django";

        WatchedDTO watchListDTO = new WatchedDTO(username, filmTitle);
        when(watchedService.createWatched(username, filmTitle)).thenReturn(watched1);

        ResponseEntity<Watched> response = watchedController.createWatched(watchListDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(watched1, response.getBody());
        verify(watchedService).createWatched(username, filmTitle);

    }


    @Test
    public void test_deleteWatched_ReturnTrue_whenValidIdPassed() {
        ObjectId id = new ObjectId();
        when(watchedService.deleteWatched(id)).thenReturn(true);

        ResponseEntity<String> response = watchedController.deleteWatched(id);

        assertNotNull(response);
        assertEquals("Film deleted from watched successfully", response.getBody());
        verify(watchedService).deleteWatched(id);
    }


    @Test
    public void test_deleteWatched_ReturnFalse_WhenInValidIdPassed() {
        ObjectId id = new ObjectId();
        when(watchedService.deleteWatched(id)).thenReturn(false);

        ResponseEntity<String> response = watchedController.deleteWatched(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(watchedService).deleteWatched(id);

    }

    


}
