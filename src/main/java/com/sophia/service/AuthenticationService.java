package com.sophia.service;

import com.sophia.entity.concrates.user.User;
import com.sophia.entity.concrates.user.UserRole;
import com.sophia.entity.enums.RoleType;
import com.sophia.payload.request.authentication.LoginRequest;
import com.sophia.payload.request.authentication.RegisterRequest;
import com.sophia.payload.response.authentication.AuthResponse;
import com.sophia.repository.UserRepository;
import com.sophia.repository.UserRoleRepository;
import com.sophia.security.jwt.JwtUtils;
import com.sophia.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final UserRoleRepository userRoleRepository;


    public ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = "Bearer " + jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        Set<String> roles =  userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Optional<String> role = roles.stream().findFirst();

        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();

        authResponse.username(userDetails.getUsername());
        authResponse.token(token.substring(7));
        role.ifPresent(authResponse::role);

        return ResponseEntity.ok(authResponse.build());

    }

    public ResponseEntity<String> registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }
        UserRole userRole = (UserRole) userRoleRepository.findByRoleType(RoleType.USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
       User user = User.builder()
               .username(registerRequest.getUsername())
                    .email(registerRequest.getEmail())
                    .password(encoder.encode(registerRequest.getPassword()))
               .userRole(userRole)
               .build();

        userRepository.save(user);




        return ResponseEntity.ok("User registered successfully!");
    }
}
