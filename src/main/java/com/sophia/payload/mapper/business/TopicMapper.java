package com.sophia.payload.mapper.business;

import com.sophia.entity.concrates.user.User;
import com.sophia.payload.request.business.topic.CreateTopicRequest;
import com.sophia.payload.response.abstracts.BaseTopicResponse;
import com.sophia.entity.concrates.business.Topic;
import com.sophia.payload.response.business.topic.BasicTopicResponse;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TopicMapper {

    private final MethodHelper methodHelper;

    public TopicResponse mapToTopicResponse(Topic topic) {
        return TopicResponse.builder()
                .topicId(topic.getId())
                .topicName(topic.getName())
                .entryCount(topic.getEntries().size())
                .creationDate(topic.getCreateDate())
                .lastModifiedDate(topic.getLastUpdateDate())
                .build();

    }
    public BasicTopicResponse mapToBasicTopicResponse(Topic topic) {
        return BasicTopicResponse.builder()
                .topicId(topic.getId())
                .topicName(topic.getName())
                .build();
    }

    public Topic mapCreateTopicRequestToTopic(CreateTopicRequest createTopicRequest, User user) {
        return Topic.builder()
                .name(methodHelper.reduceMultipleSpacesToOne(createTopicRequest.getTopicName()))
                .author(user)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();

    }

}
