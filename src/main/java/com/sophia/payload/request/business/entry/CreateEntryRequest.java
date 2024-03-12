package com.sophia.payload.request.business.entry;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEntryRequest {
    @NotBlank(message = "Entry content cannot be blank")
    @Size(min = 2, max = 10000, message = "Entry content must be between 2 and 1000 characters")
    private String content;
    private String tags;

}
