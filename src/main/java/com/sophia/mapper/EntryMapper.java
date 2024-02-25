package com.sophia.mapper;

import com.sophia.entity.concrates.business.Entry;
import com.sophia.payload.response.business.entry.EntryResponse;
import org.springframework.stereotype.Component;

@Component
public class EntryMapper {
    public EntryResponse mapToResponse(Entry entry) {
        return EntryResponse.builder()
                .entryId(entry.getId())
                .topicName(entry.getTopic().getName())
                .entryContent(entry.getContent())
                .entryOwner(entry.getAuthor().getUsername())
                .entryOwnerId(entry.getAuthor().getId())
                .likeCount(entry.getLikes().size())
                .dislikeCount(entry.getDislikes().size())
                .entryDate(entry.getCreationDate().toLocalDate().toString())
                .entryTime(entry.getCreationDate().toLocalTime().toString()).build();
}
}

