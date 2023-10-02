package com.parjalRai.films.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.DuplicateException;
import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Blocked;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.BlockedRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@Service
public class BlockedService {

    @Autowired
    private UserEntityRepository userEntityRepository;
    
    @Autowired
    private BlockedRepository blockedRepository;
    

    public List<Blocked> getByBlocker(String username) {

        UserEntity blocker = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return blockedRepository.findByBlocker(blocker);
    }
    
    public Blocked createBlocked(String blockedUsername, String blockerUsername) {

        UserEntity blocker = userEntityRepository.findByUsername(blockerUsername)
                .orElseThrow(() -> new NotFoundException("User not found"));

        UserEntity blockedUser = userEntityRepository.findByUsername(blockedUsername)
                .orElseThrow(() -> new NotFoundException("User not found"));


        if (blockedRepository.existsByBlockerAndBlockedUser(blocker, blockedUser)) {
            throw new DuplicateException("This blocker and blockedUser already exists");
                }


        Blocked blocked = new Blocked();
        blocked.setBlockedUser(blockedUser);
        blocked.setBlocker(blocker);

        return blockedRepository.save(blocked);
    }
    

    public boolean deleteBlocked(ObjectId id) {
        return blockedRepository.findById(id).map(blocked -> {
            blockedRepository.delete(blocked);
            return true; 
        }).orElse(false);

    }


    
}
