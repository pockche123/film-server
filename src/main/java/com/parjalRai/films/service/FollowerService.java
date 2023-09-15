package com.parjalRai.films.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Follower;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.FollowerRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@Service
public class FollowerService {


    @Autowired
    private FollowerRepository followerRepository;

    
    @Autowired
    private UserEntityRepository userRepository; 


    public List<Follower> findByFollowingUser(String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return followerRepository.findByFollowingUser(user);
    }
    

    public Follower createFollower(String followingUsername, String followerUsername) {

        UserEntity followingUser = userRepository.findByUsername(followingUsername)
                .orElseThrow(() -> new NotFoundException("User not found"));

        UserEntity follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Follower followerUser = new Follower();

        followerUser.setFollowingUser(followingUser);
        followerUser.setFollower(follower);

        return followerRepository.save(followerUser);
    }
    

    public boolean deleteFollower(ObjectId id) {
        return followerRepository.findById(id).map(follower -> {
            followerRepository.delete(follower);
            return true; 
        }).orElse(false);
        
    }

    
}
