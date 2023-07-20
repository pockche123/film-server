package com.parjalRai.films.model;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "discussionLikes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussionLike {

    @Id
    private ObjectId id;
    @DBRef
    private Discussion discussion;
    @DBRef
    private UserEntity userEntity;
    private Instant timestamp;
    
}
