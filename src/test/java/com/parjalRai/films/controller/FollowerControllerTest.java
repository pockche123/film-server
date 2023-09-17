package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.parjalRai.films.model.Follower;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.dto.FollowerDTO;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.FollowerService;

@ExtendWith(MockitoExtension.class)
public class FollowerControllerTest {

    @Mock
    private FollowerService followerService;

    @Mock
    private UserEntityRepository userRepository; 

    @InjectMocks
    private FollowerController followerController;

    private Follower follower1, follower2;

    @BeforeEach
    public void setup() {
        follower1 = new Follower();
        follower2 = new Follower();
    }

    @AfterEach
    public void tearDown() {
        follower1 = null;
        follower2 = null;
    }

    @Test
    void test_findByUserBeingFollowed_ReturnsResponseOk_WhenValidUsernameInputted() {

        List<Follower> expectedFollowers = Arrays.asList(follower1, follower2);

        String username = "bigman";
        UserEntity user = new UserEntity();
        user.setUsername(username);

        when(followerService.findByFollowingUser(username)).thenReturn(expectedFollowers);

        ResponseEntity<List<Follower>> response = followerController.findByUserBeingFollowed(username);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(followerService).findByFollowingUser(username);

    }

    @Test
    void test_createFollower_ReturnOkStatus_WhenValidFollowerCreated() {

        String followedUsername = "bigman";
        String followingUsername = "smallman";

        FollowerDTO followerDTO = new FollowerDTO(followedUsername, followingUsername);
        when(followerService.createFollower(followerDTO.followedUsername(), followerDTO.followingUsername()))
                .thenReturn(follower1);

        ResponseEntity<Follower> response = followerController.createFollower(followerDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(followerService).createFollower(followedUsername, followingUsername);

    }
    
    @Test
    void test_DeleteFollower_ReturnOkStatus_WhenDeleted() {
        ObjectId id = new ObjectId();
        when(followerService.deleteFollower(id)).thenReturn(true);
        
        ResponseEntity<String> response = followerController.deleteFollower(id);

        assertTrue(true);
        assertEquals("Follower deleted successfully.", response.getBody());
        verify(followerService).deleteFollower(id);

    }



}