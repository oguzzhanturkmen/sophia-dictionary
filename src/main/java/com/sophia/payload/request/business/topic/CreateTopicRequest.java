package com.sophia.payload.request.business.topic;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTopicRequest {

    @NotBlank(message = "Entry content cannot be blank")
    @Size(min = 3, max = 120, message = "Entry content must be between 2 and 1000 characters")
    private String topicName;

    @NotBlank(message = "Entry content cannot be blank")
    @Size(min = 2, max = 1000, message = "Entry content must be between 2 and 1000 characters")
    private String content;
    @NotNull
    private String[] tags;
}
