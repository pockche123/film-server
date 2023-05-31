package com.parjalRai.films.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Rating;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.RatingRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private FilmRepository filmRepository;

    public List<Rating> findAllRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> findRatingsByFilm(Film film) {
        return ratingRepository.findByFilm(film);
    }

    public List<Rating> findRatingsByUser(UserEntity user) {
        return ratingRepository.findByUserEntity(user);
    }

    public Rating createRating(String filmTitle, String username, int rate) {
        Optional<Film> optionalFilm = filmRepository.findFilmByTitleIgnoreCase(filmTitle);
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        Film film = optionalFilm.orElseThrow(() -> new NotFoundException("Film not found"));
        UserEntity user = optionalUser.orElseThrow(() -> new NotFoundException("User not found"));

        Rating rating = new Rating();
        rating.setFilm(film);
        rating.setUserEntity(user);
        rating.setRating(rate);

        return ratingRepository.save(rating);
    }

    public boolean deleteRating(ObjectId id) {
        return ratingRepository.findById(id).map(rating -> {
            ratingRepository.delete(rating);
            return true;
        }).orElse(false);
    }

    public Rating updateratingDetails(Rating rating, ObjectId id) {
        try {
            Optional<Rating> optRating = ratingRepository.findById(id);
            Rating updatedrating = optRating.get();
            if (rating.getRating() != 0) {
                updatedrating.setRating(rating.getRating());
            }
            return ratingRepository.save(updatedrating);

        } catch (Exception e) {
            System.err.println("ERROR WHILE UPDATING rating: " + e.getMessage());
        }
        return null;
    }

}
