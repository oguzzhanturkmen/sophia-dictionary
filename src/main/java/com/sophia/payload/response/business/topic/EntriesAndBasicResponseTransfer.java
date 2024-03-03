package com.sophia.payload.response.business.topic;

import com.sophia.entity.concrates.business.Entry;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EntriesAndBasicResponseTransfer {
    private BasicTopicResponse topic;
    private List<Entry> entries;
}
