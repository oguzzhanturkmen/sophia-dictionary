package com.sophia.payload.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank
    @Size(min = 3, max = 20 , message = "Username must be between 3 and 20 characters")
    private String username;

    @NotNull
    @Size(min = 6, max = 40 , message = "Password must be between 6 and 40 characters")
    private String password;
    @NotNull

    @Email
    @NotBlank
    private String email;
}
