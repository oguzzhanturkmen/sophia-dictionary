package com.sophia.controller;

import com.sophia.payload.request.authentication.LoginRequest;
import com.sophia.payload.request.authentication.RegisterRequest;
import com.sophia.payload.response.authentication.AuthResponse;
import com.sophia.payload.response.authentication.UserResponse.UserResponse;
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
    @PreAuthorize("permitAll()")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        return authenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        return authenticationService.registerUser(registerRequest);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<UserResponse> findUserByUsername(HttpServletRequest request){
        String username = (String) request.getAttribute("username");
        UserResponse userResponse = authenticationService.findUserByUsername(username);
        return ResponseEntity.ok(userResponse);


    }

}
