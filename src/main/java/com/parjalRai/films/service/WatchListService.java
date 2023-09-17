package com.parjalRai.films.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.DuplicateException;
import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.WatchList;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.repository.WatchListRepository;

@Service
public class WatchListService {


    @Autowired
    private WatchListRepository watchListRepository;

    @Autowired
    private FilmRepository filmRepository;
    
    @Autowired
    private UserEntityRepository userEntityRepository; 


    public List<WatchList> findWatchListOfAUser(UserEntity user) {
        return watchListRepository.findByUserEntity(user);
    }


    public WatchList createWatchList(String username, String filmTitle) {

        Optional<Film> optFilm = filmRepository.findFilmByTitleIgnoreCase(filmTitle);
        Optional<UserEntity> optUser = userEntityRepository.findByUsername(username);

        Film film = optFilm.orElseThrow(() -> new NotFoundException("Film Not found"));
        UserEntity user = optUser.orElseThrow(() -> new NotFoundException("User Not Found"));


        if (watchListRepository.existsByFilmAndUserEntity(film, user)) {
            throw new DuplicateException("This film already exists in this user's watch list");
        }

        WatchList watchList = new WatchList();
        watchList.setFilm(film);
        watchList.setUserEntity(user);

        return watchListRepository.save(watchList);

    }
    

    public boolean deleteWatchList(ObjectId id) {
        return watchListRepository.findById(id).map(watchList ->{
            watchListRepository.delete(watchList);
            return true; 
        }).orElse(false);
    }
    
    
}
