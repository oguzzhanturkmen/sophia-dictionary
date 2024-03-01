package com.sophia.payload.response.business.entry;

import com.sophia.entity.concrates.business.Entry;
import com.sophia.entity.concrates.business.Topic;
import com.sophia.entity.concrates.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EntryResponse {

    private Long entryId;
    private String topicName;
    private String entryContent;
    private String entryOwner;
    private Long entryOwnerId;
    private Integer likeCount;
    private Integer dislikeCount;
    private String entryDate;
    private String entryTime;

    private boolean isLiked;
    private boolean isDisliked;


}
