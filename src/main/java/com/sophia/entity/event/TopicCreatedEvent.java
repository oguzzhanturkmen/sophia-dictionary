package com.sophia.entity.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class TopicCreatedEvent extends ApplicationEvent {
    private Long topicId;

    public TopicCreatedEvent(Object source, Long topicId) {
        super(source);
        this.topicId = topicId;
    }
}
