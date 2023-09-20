package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parjalRai.films.model.Blocked;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.BlockedRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@ExtendWith(MockitoExtension.class)
public class BlockedServiceTest {
    
    @Mock
    private BlockedRepository blockedRepository;

    @Mock
    private UserEntityRepository userEntityRepository;
    
    @InjectMocks 
    private BlockedService blockedService;
    
    private Blocked blocked1, blocked2;
    
    @BeforeEach
    public void setUp() {
        blocked1 = new Blocked();
        blocked2 = new Blocked();
    }
    

    @AfterEach
    public void tearDown() {
        blocked1 = null;
        blocked2 = null;
    }
    
    @Test
    void test_getByBlocker_whenValidUsernameGiven_ReturnsAListOfBlocked() {
        
        List<Blocked> expected = Arrays.asList(blocked1, blocked2);
        String username="django"; 
        UserEntity user1 = new UserEntity(); 
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(user1));
        when(blockedRepository.findByBlocker(user1)).thenReturn(expected);
        

        List<Blocked> actual = blockedService.getByBlocker(username);
        
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(blockedRepository).findByBlocker(user1);
        
        
    }
    
    
}
