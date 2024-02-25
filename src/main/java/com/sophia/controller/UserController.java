package com.sophia.controller;

import com.sophia.payload.response.UserPorfileResponse.UserProfileResponse;
import com.sophia.payload.response.business.profile.UserFollowersResponse;
import com.sophia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<UserProfileResponse> getUserByUsername(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @GetMapping("/get/{id}/followers")
    public ResponseEntity<List<UserFollowersResponse>> getUserFollowers(@PathVariable Long id){
        return userService.getUserFollowersById(id);
    }
}
