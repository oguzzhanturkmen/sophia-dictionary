package com.sophia.payload.response.business.topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TopicResponse {

    private Long topicId;
    private String topicName;
    private Integer entryCount;

}
