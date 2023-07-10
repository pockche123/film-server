package com.parjalRai.films.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="streams")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stream {

    @Id 
    private ObjectId id;
    private String name;
    private String icon;
    private String link;
    @DBRef
    private Film film;
    private String country;
    
}
