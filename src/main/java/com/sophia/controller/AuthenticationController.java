package com.sophia.controller;

import com.sophia.payload.request.authentication.LoginRequest;
import com.sophia.payload.request.authentication.RegisterRequest;
import com.sophia.payload.request.user.UpdatePasswordRequest;
import com.sophia.payload.response.authentication.AuthResponse;
import com.sophia.payload.response.user.BasicUserResponse;
import com.sophia.payload.response.wrapper.BasicResponseMessage;
import com.sophia.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        return authenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        return authenticationService.registerUser(registerRequest);
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicUserResponse> findUserByUsername(HttpServletRequest request){
        BasicUserResponse userResponse = authenticationService.findUserByRequest(request);
        return ResponseEntity.ok(userResponse);

    }
    @PatchMapping("/user/updatePassword")
    @PreAuthorize("isAuthenticated()")
    public BasicResponseMessage updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest, HttpServletRequest request){

        return authenticationService.updatePassword(updatePasswordRequest, request);
    }

}
