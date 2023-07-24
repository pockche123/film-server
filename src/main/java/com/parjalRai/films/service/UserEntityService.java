package com.parjalRai.films.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.UserEntityRepository;

@Service
public class UserEntityService {
    

    @Autowired
    private UserEntityRepository userEntityRepository; 



    public Optional<UserEntity> findByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }
}
