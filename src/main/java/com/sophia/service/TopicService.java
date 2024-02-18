package com.sophia.service;

import com.sophia.entity.concrates.business.Topic;
import com.sophia.messages.Messages;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.mapper.TopicMapper;
import com.sophia.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;


    public Page<TopicResponse> getAllTopics(int page, int size, String sort, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (direction.equals("DESC")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return topicRepository.findAll(pageable)
                .map(topicMapper::mapToResponse);

    }

    public Topic getTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Messages.TOPIC_NOT_FOUND + id));
    }
}
