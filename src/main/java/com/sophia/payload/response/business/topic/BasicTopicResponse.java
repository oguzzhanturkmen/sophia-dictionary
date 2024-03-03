package com.sophia.payload.response.business.topic;

import com.sophia.payload.response.abstracts.BaseTopicResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor

@SuperBuilder(toBuilder = true)
public class BasicTopicResponse extends BaseTopicResponse {
}
