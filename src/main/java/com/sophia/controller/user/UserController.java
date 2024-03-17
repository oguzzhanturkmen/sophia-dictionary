package com.sophia.controller.user;

import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.payload.response.user.UserProfileResponse;
import com.sophia.payload.response.business.profile.UserFollowersResponse;
import com.sophia.payload.response.wrapper.BasicResponseMessage;
import com.sophia.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile/{id}")
    public ResponseEntity<UserProfileResponse> getUserProfileById(@PathVariable Long id){
        return userService.getUserProfileById(id);
    }
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUsersProfile(HttpServletRequest request){
        return userService.getUsersProfile(request);
    }
    @GetMapping("/profile/followers")
    public ResponseEntity<List<UserFollowersResponse>> getUserFollowers(HttpServletRequest request){
        return userService.getUsersFollowers(request);
    }
    @GetMapping("/profile/{id}/followers")
    public ResponseEntity<List<UserFollowersResponse>> getUserFollowers(@PathVariable Long id){
        return userService.getUserFollowersById(id);
    }
    @GetMapping("/profile/{id}/following")
    public ResponseEntity<List<UserFollowersResponse>> getUserFollowings(@PathVariable Long id){
        return userService.getUserFollowingsById(id);
    }
    @GetMapping("/profile/following")
    public ResponseEntity<List<UserFollowersResponse>> getUserFollowings(HttpServletRequest request){
        return userService.getUsersFollowings(request);
    }
    @GetMapping("/follow/{id}")
    public BasicResponseMessage followUser(@PathVariable Long id, HttpServletRequest request ){
        return userService.followOrUnfollowUser(id, request);
    }
    @GetMapping("/isFollowed/{id}")
    public BasicResponseMessage isFollowed(@PathVariable Long id, HttpServletRequest request){
        return userService.isFollowed(id, request);
    }
    @GetMapping("/profile/{id}/createdTopics")
    public ResponseEntity<List<TopicResponse>> getCreatedTopics(@PathVariable Long id){
        return userService.getCreatedTopics(id);
    }
    @GetMapping("/profile/createdTopics")
    public ResponseEntity<List<TopicResponse>> getUsersCreatedTopics(HttpServletRequest request){
        return userService.getUsersCreatedTopics(request);
    }
    @GetMapping("/profile/{id}/createdEntries")
    public ResponseEntity<List<EntryResponse>> getCreatedEntries(@PathVariable Long id){
        return userService.getCreatedEntries(id);
    }
    @GetMapping("/profile/createdEntries")
    public ResponseEntity<List<EntryResponse>> getUsersCreatedEntries(HttpServletRequest request){
        return userService.getUsersCreatedEntries(request);
    }
    @GetMapping("/profile/{id}/likedEntries")
    public ResponseEntity<List<EntryResponse>> getLikedEntries(@PathVariable Long id){
        return userService.getLikedEntries(id);
    }
    @GetMapping("/profile/likedEntries")
    public ResponseEntity<List<EntryResponse>> getUsersLikedEntries(HttpServletRequest request){
        return userService.getUsersLikedEntries(request);
    }
    @PostMapping("/{userId}/uploadPhoto")
    public ResponseEntity<String> uploadProfilePhoto(@PathVariable Long userId, @RequestParam("photo") MultipartFile photo) {
        try {
            // Convert photo to Base64 for storing as a string in the database
            String base64Image = Base64.getEncoder().encodeToString(photo.getBytes());

            // Assuming you have a service to update the user's profile photo
            userService.updateUserProfilePhoto(userId, base64Image);

            return ResponseEntity.ok("Photo uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the photo");
        }
    }


}
