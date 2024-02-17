package com.sophia.mapper;

import com.sophia.dto.response.TopicResponse;
import com.sophia.entity.concrates.business.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {

    public TopicResponse mapToResponse(Topic topic) {
        return TopicResponse.builder()
                .topicId(topic.getId())
                .topicName(topic.getName())
                .entryCount(topic.getEntries().size()).build();
    }

}
