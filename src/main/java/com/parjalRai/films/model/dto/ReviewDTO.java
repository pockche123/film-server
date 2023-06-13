package com.parjalRai.films.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ReviewDTO(
        String filmTitle,
        String username,
        String review,
        @Max(value=10,message="Rating cannot be greater than 10")@Min(value=0,message="Rating cannot be less than 0")
        int rating,
        @Min(value = 0, message="Rating cannot be less than 10")
        long likes

) {
}

  
