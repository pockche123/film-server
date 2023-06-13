package com.parjalRai.films.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Review;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.ReviewRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private FilmRepository filmRepository;

    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> findAReview(ObjectId objectId) {return reviewRepository.findById(objectId);}


    public Review createReview(String filmTitle, String username, String reviewText, int rating, long likes) {
        Optional<Film> optionalFilm = filmRepository.findFilmByTitleIgnoreCase(filmTitle);
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        Film film = optionalFilm.orElseThrow(() -> new NotFoundException("Film not found"));
        UserEntity user = optionalUser.orElseThrow(() -> new NotFoundException("User not found"));

        Review review = new Review();
        review.setFilm(film);
        review.setUserEntity(user);
        review.setReview(reviewText);
        review.setRating(rating);
        review.setCreatedDate(new Date());
        review.setLikes(likes);

        return reviewRepository.save(review);
    }

    public boolean deleteReview(ObjectId id) {
        return reviewRepository.findById(id).map(review -> {
            reviewRepository.delete(review);
            return true;
        }).orElse(false);
    }

    public Review updateReviewDetails(Review review, ObjectId id) {
        try {
            Optional<Review> optReview = reviewRepository.findById(id);
            Review updatedReview = optReview.get();
            if (review.getReview() != null) {
                updatedReview.setReview(review.getReview());
            }
                updatedReview.setRating(review.getRating());
                updatedReview.setLikes(review.getLikes());
            return reviewRepository.save(updatedReview);

        } catch (Exception e) {
            System.err.println("ERROR WHILE UPDATING REVIEW: " + e.getMessage());
        }
        return null;
    }



    public List<Review> findReviewsByFilm(Film film) {
        return reviewRepository.findByFilm(film);
    }

    public List<Review> findReviewsByUser(UserEntity user) {
        return reviewRepository.findByUserEntity(user);
    }


}
