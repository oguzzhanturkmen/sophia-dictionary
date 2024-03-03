package com.sophia.entity.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class EntryAddedEvent extends ApplicationEvent {
    private Long topicId;

    public EntryAddedEvent(Object source, Long topicId) {
        super(source);
        this.topicId = topicId;
    }


}
