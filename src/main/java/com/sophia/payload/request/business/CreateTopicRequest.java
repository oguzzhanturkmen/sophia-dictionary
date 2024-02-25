package com.sophia.payload.request.business;

import lombok.Data;

@Data
public class CreateTopicRequest {

    private String topicName;
    private String content;
    private String tags;
}
