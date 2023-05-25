package com.parjalRai.films.security.token;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.parjalRai.films.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "tokens")
public class Token {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String token;
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;
    private UserEntity userEntity;
}
