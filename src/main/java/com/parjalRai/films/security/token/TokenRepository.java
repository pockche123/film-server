package com.parjalRai.films.security.token;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TokenRepository extends MongoRepository<Token, ObjectId> {

    @Query("{'userEntity.username': ?0, 'expired': false, 'revoked': false}")
    List<Token> findAllValidTokensByUsername(String username);

    // Optional<Token> findByToken(String token);

    @Query("{'token': ?0, 'expired': false, 'revoked': false}")
    Stream<Token> findByTokenStream(String token);

    default Optional<Token> findByToken(String token) {
        try (Stream<Token> stream = findByTokenStream(token)) {
            return stream.findFirst();
        }
    }
    
    Optional<Token> findFirstByToken(String token);

}
