package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;


import org.springframework.http.ResponseEntity;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.parjalRai.films.model.Blocked;
import com.parjalRai.films.model.dto.BlockedDTO;
import com.parjalRai.films.service.BlockedService;

@ExtendWith(MockitoExtension.class)
public class BlockedControllerTest {
    

    @Mock
    private BlockedService blockedService;

    
    @InjectMocks
    private BlockedController blockedController;
    
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
    public void test_getAllBLockedByBlocker_returnOkResponse_WhenValidUsernameEntered() {

        List<Blocked> expected = Arrays.asList(blocked1, blocked2);
        String username = "django";
        when(blockedService.getByBlocker(username)).thenReturn(expected);

        ResponseEntity<List<Blocked>> response = blockedController.getAllBlockedByBlocker(username);

        assertNotNull(response);
        assertEquals(expected, response.getBody());
        verify(blockedService).getByBlocker(username);

    }


    @Test
    public void test_createBlocked_ReturnokStatus_WhenValidBlockedDTO_inputted() {

        String blockerUsername = "bigman";
        String blockedUsername = "smallman";

        BlockedDTO blockedDto = new BlockedDTO(blockedUsername, blockerUsername);
        when(blockedService.createBlocked(blockedDto.blockedUsername(), blockedDto.blockerUsername()))
                .thenReturn(blocked1);

        ResponseEntity<Blocked> response = blockedController.createBlocked(blockedDto);

        assertNotNull(response);
        assertEquals(blocked1, response.getBody());
        verify(blockedService).createBlocked(blockedUsername, blockerUsername);

    }
    
    @Test 
    public void test_deleteBlocked_returnTrue_whenValidIdPassed() {
        
        ObjectId id = new ObjectId();
        when(blockedService.deleteBlocked(id)).thenReturn(true);
        
        ResponseEntity<String> response = blockedController.deleteBlocked(id);
        
        assertNotNull(response);
        assertEquals("You have unblocked a user", response.getBody());
        verify(blockedService).deleteBlocked(id);
    }

    


}
