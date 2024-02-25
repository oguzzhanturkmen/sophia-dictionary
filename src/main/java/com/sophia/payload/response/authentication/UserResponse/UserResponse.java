package com.sophia.payload.response.authentication.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserResponse {

        private String username;
        private String email;
        private String role;
        private Long userId;
        private Integer entryCount;
        private Integer followerCount;
        private Integer followingCount;
        private String bio;
        private String profileImage;


}
