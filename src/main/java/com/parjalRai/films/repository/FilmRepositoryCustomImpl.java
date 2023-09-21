package com.parjalRai.films.repository;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import com.parjalRai.films.model.Film;

public class FilmRepositoryCustomImpl implements FilmRepositoryCustom {


     @Autowired
    private MongoTemplate mongoTemplate;

    
    public List<Film> findTop4FilmsByAverageRating() {
        Aggregation aggregation = Aggregation.newAggregation(
                lookup("ratings", "id", "film.id", "ratings"),
                unwind("ratings"),
                group("id").avg("ratings.rating").as("averageRating"),
                sort(Sort.Direction.DESC, "averageRating"),
                limit(4)
        );

        AggregationResults<Film> results = mongoTemplate.aggregate(aggregation, "ghibli", Film.class);
        return results.getMappedResults();
    }


}








