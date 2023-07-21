package com.parjalRai.films.model.dto;

import org.bson.types.ObjectId;

public record DiscussionLikeDTO(
        ObjectId userId,
        ObjectId discussionId
) {}
