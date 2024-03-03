package com.sophia.service.user;

import com.sophia.entity.concrates.user.User;
import com.sophia.exception.NotFoundException;
import com.sophia.messages.ErrorMessages;
import com.sophia.messages.Messages;
import com.sophia.messages.SuccessMessages;
import com.sophia.payload.mapper.business.EntryMapper;
import com.sophia.payload.mapper.business.TopicMapper;
import com.sophia.payload.mapper.user.UserMapper;
import com.sophia.payload.response.business.topic.BasicTopicResponse;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.payload.response.user.BasicUserResponse;
import com.sophia.payload.response.user.UserProfileResponse;
import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.payload.response.business.profile.UserFollowersResponse;
import com.sophia.payload.response.abstracts.BaseTopicResponse;
import com.sophia.payload.response.wrapper.BasicResponseMessage;
import com.sophia.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TopicMapper topicMapper;
    private final EntryMapper entryMapper;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);

    }
    public ResponseEntity<UserProfileResponse> getUserProfileById(Long id) {
        User user = isUserExist(id);
        return ResponseEntity.ok(userMapper.mapUserToUserProfileResponse(user));
    }
    public ResponseEntity<UserProfileResponse> getUsersProfile(HttpServletRequest request) {
        User user = isUserExist((String) request.getAttribute("username"));
        return ResponseEntity.ok(userMapper.mapUserToUserProfileResponse(user));
    }

    public ResponseEntity<List<UserFollowersResponse>> getUserFollowersById(Long id) {
        User user = isUserExist(id);
        return ResponseEntity.ok(user.getFollowers().stream().map(userMapper::mapUserToUserFollowersResponse).toList());

    }
    public ResponseEntity<List<UserFollowersResponse>> getUserFollowingsById(Long id) {
        User user = isUserExist(id);
        return ResponseEntity.ok(user.getFollowing().stream().map(userMapper::mapUserToUserFollowersResponse).toList());
    }

    public BasicResponseMessage followOrUnfollowUser(Long id, HttpServletRequest request) {
        User user = isUserExist((String) request.getAttribute("username"));
        User userToFollow = isUserExist(id);

        if (user.getId().equals(userToFollow.getId())) {
            return BasicResponseMessage.builder()
                    .message(ErrorMessages.CANT_FOLLOW_YOURSELF)
                    .state(Boolean.FALSE)
                    .build();
        }
        if (user.getFollowing().contains(userToFollow)) {
            user.getFollowing().remove(userToFollow);
            userToFollow.getFollowers().remove(user);
            userRepository.save(user);
            userRepository.save(userToFollow);
            return BasicResponseMessage.builder()
                    .message(SuccessMessages.FOLLOW_SUCCESSFULLY_REMOVED)
                    .state(Boolean.FALSE)
                    .build();
        } else {
            user.getFollowing().add(userToFollow);
            userToFollow.getFollowers().add(user);
            userRepository.save(user);
            userRepository.save(userToFollow);
            return BasicResponseMessage.builder()
                    .message(SuccessMessages.FOLLOW_SUCCESSFULLY_CREATED)
                    .state(Boolean.TRUE)
                    .build();
        }

    }

    public BasicResponseMessage isFollowed(Long id, HttpServletRequest request) {

        String username = request.getAttribute("username").toString();
        User user = userRepository.findByUsername(username);
        User userToCheck = isUserExist(id);

        return BasicResponseMessage.builder()
                .state(user.getFollowing().contains(userToCheck))
                .message(user.getFollowing().contains(userToCheck) ? Messages.USER_IS_BEING_FOLLOWED_BY_YOU : Messages.USER_IS_NOT_BEING_FOLLOWED_BY_YOU)
                .build();
    }

    public ResponseEntity<List<TopicResponse>> getCreatedTopics(Long id) {

        User user = isUserExist(id);
        return ResponseEntity.ok(user.getTopics().stream().map(topicMapper::mapToTopicResponse).toList());

    }
    public ResponseEntity<List<EntryResponse>> getLikedEntries(Long id) {
        User user = isUserExist(id);
        return ResponseEntity.ok(user.getLikes().stream().map(like -> entryMapper.mapEntryToEntryResponse(like.getEntry(), user)).toList());
    }
    private User isUserExist(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id)));
    }
    private User isUserExist(String username) {
        User user = userRepository.findByUsername(username);

        if (user.getId() == null) {
            throw new NotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_WITH_USERNAME_MESSAGE, username));
        }
        return user;

    }

    public ResponseEntity<List<UserFollowersResponse>> getUsersFollowers(HttpServletRequest request) {
        User user = isUserExist((String) request.getAttribute("username"));
        return ResponseEntity.ok(user.getFollowers().stream().map(userMapper::mapUserToUserFollowersResponse).toList());
    }

    public ResponseEntity<List<UserFollowersResponse>> getUsersFollowings(HttpServletRequest request) {
        User user = isUserExist((String) request.getAttribute("username"));
        return ResponseEntity.ok(user.getFollowing().stream().map(userMapper::mapUserToUserFollowersResponse).toList());
    }

    public ResponseEntity<List<TopicResponse>> getUsersCreatedTopics(HttpServletRequest request) {
        User user = isUserExist((String) request.getAttribute("username"));
        return ResponseEntity.ok(user.getTopics().stream().map(topicMapper::mapToTopicResponse).toList());

    }

    public ResponseEntity<List<EntryResponse>> getUsersLikedEntries(HttpServletRequest request) {
        User user = isUserExist((String) request.getAttribute("username"));
        return ResponseEntity.ok(user.getLikes().stream().map(like -> entryMapper.mapEntryToEntryResponse(like.getEntry(), user)).toList());
    }

    public ResponseEntity<List<EntryResponse>> getCreatedEntries(Long id) {
        User user = isUserExist(id);
        return ResponseEntity.ok(user.getEntries().stream().map(entry -> entryMapper.mapEntryToEntryResponse(entry, user)).toList());
    }

    public ResponseEntity<List<EntryResponse>> getUsersCreatedEntries(HttpServletRequest request) {
        User user = isUserExist((String) request.getAttribute("username"));
        return ResponseEntity.ok(user.getEntries().stream().map(entry -> entryMapper.mapEntryToEntryResponse(entry, user)).toList());
    }
}
