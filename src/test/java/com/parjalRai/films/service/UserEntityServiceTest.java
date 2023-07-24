package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyChar;
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

import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.UserEntityRepository;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceTest {
    
    @Mock
    private UserEntityRepository userEntityRepository;
    
    @InjectMocks
    private UserEntityService userEntityService;

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
    public void testFindByUsername_ReturnAUser_WhenUserFound() {
        String username = "user1";
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(user1));

        Optional<UserEntity> actualUser = userEntityService.findByUsername(username);
        
        assertEquals(Optional.of(user1), actualUser);
        verify(userEntityRepository).findByUsername(username);

    }
}
