package com.sophia.service;

import com.sophia.entity.concrates.business.Entry;
import com.sophia.entity.concrates.business.Topic;
import com.sophia.mapper.EntryMapper;
import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.payload.response.business.topic.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final TopicService topicService;
    private final EntryMapper entryMapper;


    public Page<EntryResponse> getAllEntries(Long id, int page, int size, String sort, String direction) {

        //************* Problem with sorting *********
        Topic topic = topicService.getTopicById(id);
        List<Entry> entries = topic.getEntries();

        List<EntryResponse> entryResponses = entries.stream()
                .map(entryMapper::mapToResponse)
                .collect(Collectors.toList());

        Page<EntryResponse> entryResponsePage = new PageImpl<>(entryResponses, PageRequest.of(page, size).withSort(Sort.by(sort).ascending()), entries.size());
        if (Objects.equals(direction, "DESC")) {
            entryResponsePage = new PageImpl<>
                    (entryResponses, PageRequest.of(page, size).withSort(Sort.by(sort).descending()), entries.size());
        }

        return entryResponsePage;
    }
}