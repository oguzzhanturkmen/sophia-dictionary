package com.sophia.payload.response.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
public class ProfileFollowersAndFollowingResponse {

    private Long userId;
    private String username;
    private String profileImage;
    private Integer entryCount;

}
