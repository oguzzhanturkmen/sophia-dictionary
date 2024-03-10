package com.sophia.payload.response.business.entry;

import com.sophia.payload.response.abstracts.BaseEntryResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class EntryResponse extends BaseEntryResponse {
    private boolean isLiked;
    private boolean isDisliked;
    private String entryTitle;
}
