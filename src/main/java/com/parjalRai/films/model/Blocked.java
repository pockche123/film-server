package com.parjalRai.films.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "blocked")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blocked {

    @Id
    private ObjectId id;
    @DBRef
    private UserEntity blockedUser;
    @DBRef
    private UserEntity blocker; 
    
    
}
