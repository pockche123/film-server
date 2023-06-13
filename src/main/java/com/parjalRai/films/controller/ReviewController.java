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
import com.parjalRai.films.model.Review;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.model.dto.ReviewDTO;
import com.parjalRai.films.repository.UserEntityRepository;
import com.parjalRai.films.service.FilmService;
import com.parjalRai.films.service.ReviewService;

@RestController
@RequestMapping("api/v1/review")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private FilmService filmService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.findAllReviews());
    }

    @GetMapping("/{objectId}")
    public ResponseEntity<Optional<Review>> getAReview(@PathVariable ObjectId objectId){
        return ResponseEntity.ok(reviewService.findAReview(objectId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable ObjectId id) {
        boolean deleted = reviewService.deleteReview(id);
        if (deleted) {
            return ResponseEntity.ok("Review deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            Review review = reviewService.createReview(reviewDTO.filmTitle(), reviewDTO.username(),
                     reviewDTO.review(), reviewDTO.rating(), reviewDTO.likes());
            return ResponseEntity.ok(review);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Review> updateReview(@RequestBody Review review, @PathVariable ObjectId id) {
        Review updatedReview = reviewService.updateReviewDetails(review, id);
        if (updatedReview != null) {
            return ResponseEntity.ok(updatedReview);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/film/{filmTitle}")
    public List<Review> getFilmReviews(@PathVariable String filmTitle) {
        Optional<Film> optFilm = filmService.findFilmByTitle(filmTitle);
        Film film = optFilm.get();
        List<Review> reviews = reviewService.findReviewsByFilm(film);
        return reviews;
    }
    
    @GetMapping("/user/{username}")
    public List<Review> getUserReviews(@PathVariable String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        UserEntity user = optUser.get();
        List<Review> reviews = reviewService.findReviewsByUser(user);
        return reviews;
    }
}
