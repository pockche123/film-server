package com.parjalRai.films.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.model.Rating;
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




    
}
