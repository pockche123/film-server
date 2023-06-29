package com.parjalRai.films.model.dto;


import org.bson.types.ObjectId;


public record CommentDTO(

ObjectId discussionId,
ObjectId commentId,
String text,
String username,
Long likes

) {}
