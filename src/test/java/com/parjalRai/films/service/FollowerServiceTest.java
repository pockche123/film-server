package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.parjalRai.films.model.Follower;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.FollowerRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@ExtendWith(MockitoExtension.class)
public class FollowerServiceTest {

    @Mock
    private FollowerRepository followerRepository;
    
    @Mock
    private UserEntityRepository userRepository;
    

    @InjectMocks
    private FollowerService followerService;

    private Follower follower1, follower2; 
    
    @BeforeEach
    public void setUp() {
        follower1 = new Follower();
        follower2 = new Follower();

    }
    
    @AfterEach
    public void tearDown() {
        follower1 = null;
        follower2 = null;
    }
    
    @Test
    public void testFindByFollowingUser_ReturnsAListOfUser_WhenValidUsernameGiven() {
        String username = "bigman";
        UserEntity user = new UserEntity();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        List<Follower> expectedFollowers = Arrays.asList(follower1, follower2);
        when(followerRepository.findByFollowingUser(user)).thenReturn(expectedFollowers);

        List<Follower> actualFollowers = followerService.findByFollowingUser(username);

        assertNotNull(actualFollowers);
        assertEquals(expectedFollowers, actualFollowers);
        verify(followerRepository).findByFollowingUser(user);

    }
    

    @Test
    public void testCreateFollower_ReturnsAFollower_WhenCreated() {

        String followedUsername = "bigman";
        UserEntity followingUser = new UserEntity();
        when(userRepository.findByUsername(followedUsername)).thenReturn(Optional.of(followingUser));
        String followerUsername = "smallman";
        UserEntity followedUser = new UserEntity();
        when(userRepository.findByUsername(followerUsername)).thenReturn(Optional.of(followedUser));
        follower1.setFollower(followedUser);
        follower1.setFollowingUser(followingUser);
        when(followerRepository.save(ArgumentMatchers.any(Follower.class))).thenReturn(follower1);

        Follower actualFollower = followerService.createFollower(followedUsername, followerUsername);

        assertNotNull(actualFollower);
        assertEquals(follower1, actualFollower);
        verify(followerRepository).save(any());
    }
    

    @Test
    public void testDeleteFollower_ReturnTrue_WhenIdFound() {
        ObjectId id = new ObjectId();
        when(followerRepository.findById(id)).thenReturn(Optional.of(follower1));

        boolean result = followerService.deleteFollower(id);
        
        assertTrue(result);
        verify(followerRepository).delete(follower1);
    }

    
}
