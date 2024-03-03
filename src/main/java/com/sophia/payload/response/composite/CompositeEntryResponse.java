package com.sophia.payload.response.composite;

import com.sophia.payload.response.abstracts.BaseEntryResponse;
import com.sophia.payload.response.business.topic.BasicTopicResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class CompositeEntryResponse {
    private BasicTopicResponse topic;
    private Page<? extends BaseEntryResponse> entries;
}
