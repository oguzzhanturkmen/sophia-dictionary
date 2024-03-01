package com.sophia.payload.mapper.business;

import com.sophia.payload.response.business.topic.TopicResponse;
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
