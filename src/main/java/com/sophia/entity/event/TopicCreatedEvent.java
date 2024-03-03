package com.sophia.entity.event;

import com.sophia.entity.concrates.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class TopicCreatedEvent extends ApplicationEvent {
    private final Long topicId;
    private final String  username;
    private final String content;

    public TopicCreatedEvent(Object source, Long topicId, String username, String content) {
        super(source);
        this.topicId = topicId;
        this.username = username;
        this.content = content;
    }
}
