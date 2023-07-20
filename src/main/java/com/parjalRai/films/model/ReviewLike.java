package com.parjalRai.films.model;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "reviewLikes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewLike {

    @Id
    private ObjectId id;
    @DBRef
    private Review review;
    @DBRef
    private UserEntity userEntity;
    private Instant timestamp;
}
