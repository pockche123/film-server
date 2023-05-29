package com.parjalRai.films.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "ghibli")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {


    @Id
    private ObjectId id;
    private String imdbId;
    private String title;
    private String releaseDate;
    private Integer year;
    private List<String> genres;
    private String poster;
    private String quote;
    private String overview;
    private String trailerLink;
    private List<String> backdrops;
    // @DocumentReference
    // private List<Review> reviews;
    private String pgRating;
    private Integer duration;



}
