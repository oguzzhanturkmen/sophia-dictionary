package com.sophia.payload.response.user;

import com.sophia.payload.response.abstracts.BaseUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor

@SuperBuilder(toBuilder = true)
public class BasicUserResponse extends BaseUserResponse {
}
