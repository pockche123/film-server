package com.parjalRai.films.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Rating;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.dto.RatingDTO;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.FilmService;
import com.parjalRai.films.service.RatingService;

@RestController
@RequestMapping("api/v1/rating")
@CrossOrigin(origins = "http://localhost:3000")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private UserEntityRepository userRepository; 

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        return ResponseEntity.ok(ratingService.findAllRatings());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleterating(@PathVariable ObjectId id) {
        boolean deleted = ratingService.deleteRating(id);
        if (deleted) {
            return ResponseEntity.ok("Rating deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Rating> createrating(@RequestBody RatingDTO ratingDTO) {
        try {
            Rating rating = ratingService.createRating(ratingDTO.filmTitle(), ratingDTO.username(),
                    ratingDTO.rating());
            return ResponseEntity.ok(rating);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Rating> updaterating(@RequestBody Rating rating, @PathVariable ObjectId id) {
        Rating updatedrating = ratingService.updateratingDetails(rating, id);
        if (updatedrating != null) {
            return ResponseEntity.ok(updatedrating);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/film/{filmTitle}")
    public List<Rating> getFilmratings(@PathVariable String filmTitle) {
        Optional<Film> optFilm = filmService.findFilmByTitle(filmTitle);
        Film film = optFilm.get();
        List<Rating> ratings = ratingService.findRatingsByFilm(film);
        return ratings;
    }


    @GetMapping("/average/film/{title}")
    public double getAverageFilmRating(@PathVariable String title) {
        Optional<Film> optFilm = filmService.findFilmByTitle(title); 
        Film film = optFilm.get();
        List<Rating> ratings = ratingService.findRatingsByFilm(film);
        

        double result = 0;
        
        for (Rating rating : ratings) {
            result += rating.getRating();
        }

        double averageRating = Math.round((result / ratings.size()) * 100.0) / 100.0; 
        return averageRating; 
    }

    @GetMapping("/user/{username}")
    public List<Rating> getUserRatings(@PathVariable String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        UserEntity user = optUser.get();
        List<Rating> ratings = ratingService.findRatingsByUser(user);
        return ratings;
    }
}
