package com.sophia.payload.response.business.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserFollowersResponse {

        private String username;
        private Long userId;
        private Integer entryCount;
        private String bio;
        private String profileImage;
}
