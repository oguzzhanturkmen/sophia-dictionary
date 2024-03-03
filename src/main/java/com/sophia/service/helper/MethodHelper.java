package com.sophia.service.helper;

import org.springframework.stereotype.Component;

@Component
public class MethodHelper {
    public  String reduceMultipleSpacesToOne(String input) {

        return input.trim().replaceAll("\\s+", " ");
    }
}
