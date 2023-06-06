package com.parjalRai.films.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    

    @Id
    private ObjectId reviewId;
    @DBRef
    private Film film;
    @DBRef
    private UserEntity userEntity;
    private String review;
    private int rating;
    @CreatedDate
    private Date createdDate;


}
