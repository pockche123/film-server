package com.parjalRai.films.model.dto;

import org.bson.types.ObjectId;

public record CommentLikeDTO(
    
ObjectId userId,
ObjectId commentId


){}
