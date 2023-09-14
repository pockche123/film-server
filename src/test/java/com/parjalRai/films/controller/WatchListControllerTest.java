package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.parjalRai.films.model.WatchList;
import com.parjalRai.films.model.dto.WatchListDTO;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.WatchListService;

@ExtendWith(MockitoExtension.class)
public class WatchListControllerTest {


    @Mock
    private WatchListService watchListService;
    
    @Mock
    private UserEntityRepository userRepository;
    
    @InjectMocks
    private WatchListController watchListController;
    

    private WatchList watchList1, watchList2;
    
    @BeforeEach
    public void setUp() {
        watchList1 = new WatchList();
        watchList2 = new WatchList();
    }

    @AfterEach 
    public void tearDown() {
        watchList1 = null;
        watchList2 = null;
    }

    @Test
    public void test_getUserWatchList_withValidUsername_ReturnsAListOfWatchList() {
        String username = "bigman";
        UserEntity user = new UserEntity();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        List<WatchList> expectedList = Arrays.asList(watchList1, watchList2);
        when(watchListService.findWatchListOfAUser(user)).thenReturn(expectedList);

        ResponseEntity<List<WatchList>> response = watchListController.getUserWatchList(username);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
        verify(watchListService).findWatchListOfAUser(user);
    }
    

    @Test
    public void test_createWatchList_ReturnsOkStatus_WhenWatchListCreated() {
        String username = "user1";
        String filmTitle = "django";

        WatchListDTO watchListDTO = new WatchListDTO(username, filmTitle);
        when(watchListService.createWatchList(username, filmTitle)).thenReturn(watchList1);

        ResponseEntity<WatchList> response = watchListController.createWatchList(watchListDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(watchList1, response.getBody());
        verify(watchListService).createWatchList(username, filmTitle);

    }
    
    @Test
    public void test_deleteWatchList_ReturnTrue_whenValidIdPassed() {
        ObjectId id = new ObjectId();
        when(watchListService.deleteWatchList(id)).thenReturn(true);

        ResponseEntity<String> response = watchListController.deleteWatchList(id);

        assertNotNull(response);
        assertEquals("Film deleted from watchList successfully", response.getBody());
        verify(watchListService).deleteWatchList(id);
    }
    

    @Test
    public void test_deleteWatchList_ReturnFalse_WhenInValidIdPassed() {
        ObjectId id = new ObjectId();
        when(watchListService.deleteWatchList(id)).thenReturn(false);


        ResponseEntity<String> response = watchListController.deleteWatchList(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(watchListService).deleteWatchList(id);

    }

    
    


}
