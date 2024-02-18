package com.sophia.payload.response.business.entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EntryResponse {

    private Long entryId;
    private String topicName;
    private String entryContent;
    private String entryOwner;
    private Integer likeCount;
    private Integer dislikeCount;
    private String entryDate;
    private String entryTime;

}
