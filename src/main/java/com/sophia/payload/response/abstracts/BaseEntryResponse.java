package com.sophia.payload.response.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseEntryResponse {

    private Long entryId;
    private String entryContent;
    private String entryAuthor;
    private Long entryAuthorId;
    private Integer likeCount;
    private Integer dislikeCount;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate entryDate;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime entryTime;


}
