package com.parjalRai.films.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "watchList")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchList {

    @Id
    private ObjectId id;
    @DBRef
    private Film film;
    @DBRef
    private UserEntity userEntity;
    
}
