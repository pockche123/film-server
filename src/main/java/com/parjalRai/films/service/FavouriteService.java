package com.parjalRai.films.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.DuplicateException;
import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Favourite;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.FavouriteRepository;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@Service
public class FavouriteService {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private FilmRepository filmRepository;

    public List<Favourite> findAllFavorites() {

        return favouriteRepository.findAll();
    }

    public List<Favourite> findAllFavouritesByUsername(String username) {

        UserEntity user = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("This user doesn't exists"));

        return favouriteRepository.findByUserEntity(user);

    }

    public Favourite createFavourite(String username, String filmTitle) {

        UserEntity user = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("This user doesn't exists"));

        Film film = filmRepository.findFilmByTitleIgnoreCase(filmTitle)
                .orElseThrow(() -> new NotFoundException("This film is not found"));


        if (favouriteRepository.existsByUserEntityAndFilm(user, film)) {
            throw new DuplicateException("This film is already favourited by this user");
            }

        Favourite favourite = new Favourite();
        favourite.setFilm(film);
        favourite.setUserEntity(user);

        return favouriteRepository.save(favourite);

    }

    public boolean deleteFavourite(ObjectId id) {
        
        return favouriteRepository.findById(id)
                .map(fav -> {
                    favouriteRepository.delete(fav);
                    return true;
                }).orElse(false);

}
    
    
}
