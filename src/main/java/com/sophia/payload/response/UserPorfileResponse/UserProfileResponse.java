package com.sophia.payload.response.UserPorfileResponse;

import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.payload.response.business.topic.TopicResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserProfileResponse {

    private String username;
    private String email;
    private Long userId;
    private Integer entryCount;
    private Integer followerCount;
    private Integer followingCount;
    private String bio;
    private String profileImage;
    private List<EntryResponse> entries;
    private List<TopicResponse> topics;

}
