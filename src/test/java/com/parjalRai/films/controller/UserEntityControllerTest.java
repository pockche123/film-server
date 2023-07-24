package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
import com.parjalRai.films.service.UserEntityService;

@ExtendWith(MockitoExtension.class)
public class UserEntityControllerTest {
    
    @Mock
    private UserEntityService userEntityService;
    
    @InjectMocks
    private UserEntityController userEntityController;
    

    private UserEntity user1;

    @BeforeEach
    public void setUp() {
        user1 = new UserEntity();

    }

    @AfterEach
    public void tearDown() {
        user1 = null;
    }

    @Test
    public void testGetUserByUsername_ReturnOkStatus_WhenUserEntityFound() {
        String username = "user1";
        when(userEntityService.findByUsername(username)).thenReturn(Optional.of(user1));

        ResponseEntity<Optional<UserEntity>> response = userEntityController.getAUserByUsername(username);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(user1), response.getBody());
        verify(userEntityService).findByUsername(username);
        
    }

}


