package com.sophia.payload.response.userPorfileResponse;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
public class ProfileFollowersAndFollowingResponse {

    private Long id;
    private String username;
    private String profileImage;
    private String bio;

}
