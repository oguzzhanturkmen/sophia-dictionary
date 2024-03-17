package com.sophia.payload.mapper.user;

import com.sophia.entity.concrates.user.User;

import com.sophia.payload.response.business.profile.UserFollowersResponse;
import com.sophia.payload.response.user.BasicUserResponse;
import com.sophia.payload.response.user.UserProfileResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public BasicUserResponse mapUserToBasicUserResponse(User user) {
        return BasicUserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getUserRole().getRoleName())
                .build();
    }

    public UserProfileResponse mapUserToUserProfileResponse(User user) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .followingCount(user.getFollowing().size())
                .followerCount(user.getFollowers().size())
                .entryCount(user.getEntries().size())
                .bio(user.getBio())
                .profileImage(Base64.encodeBase64String(user.getProfileImage()))
                .build();
    }
    public UserFollowersResponse mapUserToUserFollowersResponse(User user) {
        return UserFollowersResponse.builder()
                .username(user.getUsername())
                .userId(user.getId())
                .entryCount(user.getEntries().size())
                .bio(user.getBio())
                .profileImage(Base64.encodeBase64String(user.getProfileImage()))
                .build();
    }
}
