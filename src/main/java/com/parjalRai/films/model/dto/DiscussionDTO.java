package com.parjalRai.films.model.dto;

public record DiscussionDTO(
    String filmTitle,
    String username,
    String title,
    String description,
    Long likes
    
) {}
