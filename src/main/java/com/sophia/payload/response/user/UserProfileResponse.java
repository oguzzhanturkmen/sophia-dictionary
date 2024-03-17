package com.sophia.payload.response.user;

import com.sophia.payload.response.abstracts.BaseUserResponse;
import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.payload.response.abstracts.BaseTopicResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

@SuperBuilder(toBuilder = true)
public class UserProfileResponse extends BaseUserResponse {


    private Integer entryCount;
    private Integer followerCount;
    private Integer followingCount;
    private String bio;
    private String profileImage;




}
