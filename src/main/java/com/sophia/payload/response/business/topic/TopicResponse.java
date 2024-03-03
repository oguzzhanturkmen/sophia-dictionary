package com.sophia.payload.response.business.topic;

import com.sophia.payload.response.abstracts.BaseTopicResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TopicResponse extends BaseTopicResponse {

    private Integer entryCount;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
}
