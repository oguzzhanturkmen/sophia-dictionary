package com.sophia.payload.request.business;

import lombok.Data;

@Data
public class CreateEntryRequest {
    private String content;
    private String tags;


}
