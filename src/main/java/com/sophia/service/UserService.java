package com.sophia.service;

import com.sophia.entity.concrates.user.User;
import com.sophia.payload.response.UserPorfileResponse.UserProfileResponse;
import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.payload.response.business.profile.UserFollowersResponse;
import com.sophia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);

    }

    public ResponseEntity<UserProfileResponse> getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

       List<EntryResponse> entryResponses = user.getEntries().stream().map(entry -> EntryResponse.builder()
                .entryId(entry.getId())
                .topicName(entry.getTopic().getName())
                .entryContent(entry.getContent())
                .entryOwner(entry.getAuthor().getUsername())
                .entryOwnerId(entry.getAuthor().getId())
                .likeCount(entry.getLikes().size())
                .dislikeCount(entry.getDislikes().size())
                .entryDate(entry.getCreationDate().toLocalDate().toString())
                .entryTime(entry.getCreationDate().toLocalTime().toString())
                .build()).toList();

        UserProfileResponse userProfileResponse = UserProfileResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .userId(user.getId())
                .entryCount(user.getEntries().size())
                .followerCount(user.getFollowers().size())
                .followingCount(user.getFollowing().size())
                .profileImage(user.getProfileImage())
                .entries(entryResponses)
                .bio(user.getBio())

                .build();
        return ResponseEntity.ok(userProfileResponse);
    }

    public ResponseEntity<List<UserFollowersResponse>> getUserFollowersById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<UserFollowersResponse> userFollowersResponse = user.getFollowers().stream().map(follower -> UserFollowersResponse.builder()
                .username(follower.getUsername())
                .userId(follower.getId())
                .entryCount(follower.getEntries().size())
                .bio(follower.getBio())
                .profileImage(follower.getProfileImage())
                .build()).toList();

        return ResponseEntity.ok(userFollowersResponse);

    }
}
