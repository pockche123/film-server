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
import com.parjalRai.films.model.Watched;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.repository.WatchedRepository;

@Service
public class WatchedService {

    @Autowired
    private WatchedRepository watchedRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;


    public List<Watched> findWatchListOfAUser(UserEntity user) {
        return watchedRepository.findByUserEntity(user);
    }


    public Watched createWatched(String username, String filmTitle) {

        Optional<Film> optFilm = filmRepository.findFilmByTitleIgnoreCase(filmTitle);
        Optional<UserEntity> optUser = userEntityRepository.findByUsername(username);

        Film film = optFilm.orElseThrow(() -> new NotFoundException("Film Not found"));
        UserEntity user = optUser.orElseThrow(() -> new NotFoundException("User Not Found"));

        if (watchedRepository.existsByFilmAndUserEntity(film, user)) {
            throw new DuplicateException("This film already exists in this user's watched list");
        }

        Watched watched = new Watched();
        watched.setFilm(film);
        watched.setUserEntity(user);

        return watchedRepository.save(watched);

    }


    public boolean deleteWatched(ObjectId id) {
        return watchedRepository.findById(id).map(watchList -> {
            watchedRepository.delete(watchList);
            return true;
        }).orElse(false);
    }


    
    
}
