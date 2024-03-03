package com.sophia.payload.response.business.entry;

import com.sophia.payload.response.abstracts.BaseEntryResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor

@SuperBuilder(toBuilder = true)
public class PublicEntryResponse extends BaseEntryResponse {

}
