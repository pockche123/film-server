package com.parjalRai.films.model.dto;

import org.bson.types.ObjectId;

public record ReviewLikeDTO( 
     ObjectId userId,
     ObjectId reviewId
     
) {}

