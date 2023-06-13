package com.parjalRai.films.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "discussions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discussion {

    @Id
    private ObjectId id;
    @DBRef
    private Film film;
    @DBRef
    private UserEntity user;
    private String title; 
    private String description; 
    private Instant timestamp;
    
}
