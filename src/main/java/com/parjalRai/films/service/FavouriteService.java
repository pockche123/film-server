package com.parjalRai.films.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Favourite;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.FavouriteRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@Service
public class FavouriteService {


    @Autowired
    private FavouriteRepository favouriteRepository;
    
    @Autowired
    private UserEntityRepository userEntityRepository;
    

    public List<Favourite> findAllFavorites() {

        return favouriteRepository.findAll();
    }
    

    public List<Favourite> findAllFavouritesByUsername(String username ) {
        
        UserEntity user = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("This user doesn't exists"));

        return favouriteRepository.findByUserEntity(user);
                

    }
    
    
}
