package com.sophia.service.helper;

import com.sophia.payload.request.business.entry.CreateEntryRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MethodHelper {
    public  String reduceMultipleSpacesToOne(String input) {

        return input.trim().replaceAll("\\s+", " ");
    }

    public List<String> tagNameExtractor(CreateEntryRequest entryRequest) {
        return Arrays.stream(entryRequest.getContent().toLowerCase()
                        .split(" ")).filter(word -> word.startsWith("#"))
                .map(word -> word.substring(1))
                .toList();
    }
}
