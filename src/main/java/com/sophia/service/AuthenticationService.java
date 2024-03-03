package com.sophia.service;

import com.sophia.entity.concrates.user.User;
import com.sophia.entity.concrates.user.UserRole;
import com.sophia.entity.enums.RoleType;
import com.sophia.exception.NotFoundException;
import com.sophia.messages.ErrorMessages;
import com.sophia.messages.SuccessMessages;
import com.sophia.payload.request.authentication.LoginRequest;
import com.sophia.payload.request.authentication.RegisterRequest;
import com.sophia.payload.request.user.UpdatePasswordRequest;
import com.sophia.payload.response.authentication.AuthResponse;
import com.sophia.payload.response.user.BasicUserResponse;
import com.sophia.payload.response.wrapper.BasicResponseMessage;
import com.sophia.repository.user.UserRepository;
import com.sophia.repository.user.UserRoleRepository;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
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
                    .body(ErrorMessages.USERNAME_ALREADY_EXISTS);
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorMessages.EMAIL_ALREADY_EXISTS);
        }
        UserRole userRole = (UserRole) userRoleRepository.findByRoleType(RoleType.USER)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.ROLE_NOT_FOUND));
       User user = User.builder()
               .username(registerRequest.getUsername())
                    .email(registerRequest.getEmail())
                    .password(encoder.encode(registerRequest.getPassword()))
               .userRole(userRole)
               .build();

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    public BasicUserResponse findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (Objects.isNull(user)) {
            throw new NotFoundException(ErrorMessages.NOT_FOUND_USER_WITH_USERNAME_MESSAGE);
        }

        return BasicUserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getUserRole().getRoleName())
                .build();
    }
    public BasicResponseMessage updatePassword(UpdatePasswordRequest updatePasswordRequest, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        User user = userRepository.findByUsername(username);

        if(!Boolean.TRUE.equals(updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getNewPasswordSecond()))){
            return  BasicResponseMessage.builder()
                    .message(ErrorMessages.PASSWORDS_DOES_NOT_MATCH)
                    .build();
        }
        if(!encoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())){
            return  BasicResponseMessage.builder()
                    .message(ErrorMessages.WRONG_PASSWORD)
                    .build();
        }
        user.setPassword(encoder.encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(user);

        return BasicResponseMessage.builder()
                .message(SuccessMessages.PASSWORD_UPDATED_SUCCESSFULLY)
                .build();
    }

    public BasicUserResponse findUserByRequest(HttpServletRequest request) {
        return findUserByUsername((String) request.getAttribute("username"));
    }
}
