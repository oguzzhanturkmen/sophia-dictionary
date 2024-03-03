package com.sophia.payload.mapper.business;

import com.sophia.entity.concrates.business.Entry;
import com.sophia.entity.concrates.business.Topic;
import com.sophia.entity.concrates.user.User;
import com.sophia.payload.request.business.entry.CreateEntryRequest;
import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.payload.response.business.entry.PublicEntryResponse;
import com.sophia.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EntryMapper {

    private final MethodHelper methodHelper;
    public EntryResponse mapEntryToEntryResponse(Entry entry, User user) {
        return EntryResponse.builder()
                .entryId(entry.getId())
                .entryContent(entry.getContent())
                .entryAuthor(entry.getAuthor().getUsername())
                .entryAuthorId(entry.getAuthor().getId())
                .likeCount(entry.getLikes().size())
                .dislikeCount(entry.getDislikes().size())
                .entryDate(entry.getCreationDate().toLocalDate())
                .entryTime(entry.getCreationDate().toLocalTime())
                .isLiked(isLikedValidation(entry, user))
                .isDisliked(isDislikedValidation(entry, user))
                .build();
}
    public PublicEntryResponse mapEntryToPublicEntryResponse(Entry entry) {
        return PublicEntryResponse.builder()
                .entryId(entry.getId())
                .entryContent(entry.getContent())
                .entryAuthor(entry.getAuthor().getUsername())
                .entryAuthorId(entry.getAuthor().getId())
                .likeCount(entry.getLikes().size())
                .dislikeCount(entry.getDislikes().size())
                .entryDate(entry.getCreationDate().toLocalDate())
                .entryTime(entry.getCreationDate().toLocalTime())
                .build();
    }
    public Entry mapCreateEntryRequestToEntry(CreateEntryRequest createEntryRequest, User user, Topic topic) {
        return Entry.builder()
                .content(methodHelper.reduceMultipleSpacesToOne(createEntryRequest.getContent()))
                .author(user)
                .topic(topic)
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }


    private Boolean isLikedValidation(Entry entry, User user) {
        return entry.getLikes().stream().anyMatch(like -> like.getUser().equals(user));
    }
    private Boolean isDislikedValidation(Entry entry, User user) {
        return entry.getDislikes().stream().anyMatch(dislike -> dislike.getUser().equals(user));
    }
}

