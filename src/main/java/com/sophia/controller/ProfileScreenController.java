package com.sophia.controller;

import com.sophia.mapper.TopicMapper;
import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileScreenController {

    private final UserService userService;


    @GetMapping("/follow/{id}")
    public ResponseEntity<String> followUser(@PathVariable Long id, HttpServletRequest request ){
        return userService.followOrUnfollowUser(id, request);
    }
    @GetMapping("/get/isFollowed/{id}")
    public ResponseEntity<Boolean> isFollowed(@PathVariable Long id, HttpServletRequest request){
        return userService.isFollowed(id, request);
    }
    @GetMapping("/get/{id}/createdTopics")
    public ResponseEntity<List<TopicResponse>> getCreatedTopics(@PathVariable Long id){
        return userService.getCreatedTopics(id);
    }
    @GetMapping("/get/{id}/likedEntries")
    public ResponseEntity<List<EntryResponse>> getLikedEntries(@PathVariable Long id){
        return userService.getLikedEntries(id);
    }




}
