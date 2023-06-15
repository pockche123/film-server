package com.parjalRai.films.model;


import java.time.Instant;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {


    @Id
    private ObjectId id;
    private String text;
    private Instant timestamp;
    @DBRef
    private Comment parentComment;
    @DBRef
    private Discussion discussion;
    @DBRef
    private UserEntity user;
    private long likes;
    public void setId(ObjectId id2) {
    } 

    
}
