package com.sophia.payload.mapper.business;

import com.sophia.entity.concrates.business.Entry;
import com.sophia.entity.concrates.user.User;
import com.sophia.payload.response.business.entry.EntryResponse;
import org.springframework.stereotype.Component;

@Component
public class EntryMapper {
    public EntryResponse mapToResponse(Entry entry, User user) {
        return EntryResponse.builder()
                .entryId(entry.getId())
                .topicName(entry.getTopic().getName())
                .entryContent(entry.getContent())
                .entryOwner(entry.getAuthor().getUsername())
                .entryOwnerId(entry.getAuthor().getId())
                .likeCount(entry.getLikes().size())
                .dislikeCount(entry.getDislikes().size())
                .entryDate(entry.getCreationDate().toLocalDate().toString())
                .entryTime(entry.getCreationDate().toLocalTime().toString()).
                isLiked(isLikedValidation(entry, user))
                .isDisliked(isDislikedValidation(entry, user))
                .build();
}

public Boolean isLikedValidation(Entry entry, User user) {
    return entry.getLikes().stream().anyMatch(like -> like.getUser().equals(user));
}
public Boolean isDislikedValidation(Entry entry, User user) {
    return entry.getDislikes().stream().anyMatch(dislike -> dislike.getUser().equals(user));
}
}

