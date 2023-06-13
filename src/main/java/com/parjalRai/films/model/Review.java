package com.parjalRai.films.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    //might need to change this to Timestamp
    @CreatedDate
    private Date createdDate;
    private long likes;

    private Set<UserEntity> likedBy  = new HashSet<>();

    // Constructors, Getters, Setters, and other methods

    // public Review() {
    //     this.likedBy = new HashSet<>();
    // }

    public void addLikedBy(UserEntity user) {
        likedBy.add(user);
    }

    public void removeLikedBy(UserEntity user) {
        likedBy.remove(user);
    }

    public boolean isLikedBy(UserEntity user) {
        return likedBy.contains(user);
    }

}
