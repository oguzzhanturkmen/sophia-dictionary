package com.sophia.payload.mapper.business;

import com.sophia.entity.concrates.business.ChannelTag;
import com.sophia.entity.concrates.user.User;
import com.sophia.exception.NotFoundException;
import com.sophia.payload.request.business.topic.CreateTopicRequest;
import com.sophia.payload.response.abstracts.BaseTopicResponse;
import com.sophia.entity.concrates.business.Topic;
import com.sophia.payload.response.business.topic.BasicTopicResponse;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.repository.business.ChannelRepository;
import com.sophia.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TopicMapper {

    private final MethodHelper methodHelper;
    private final ChannelRepository channelRepository;

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
        List<ChannelTag> channelTags = new ArrayList<>();
        for (String channelTagName : createTopicRequest.getTags()) {
            ChannelTag channelTag = channelRepository.findByName(channelTagName).orElseThrow(() -> new NotFoundException("Channel Tag not found"));
            channelTags.add(channelTag);
        }
        return Topic.builder()
                .name(methodHelper.reduceMultipleSpacesToOne(createTopicRequest.getTopicName()))
                .author(user)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .channelTags(channelTags)
                .build();

    }

}
