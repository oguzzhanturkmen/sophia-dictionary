package com.sophia.controller;

import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainScreenController {

    private final TopicService topicService;

    @GetMapping
    public Page<TopicResponse> getAllTopics(@RequestParam(value = "page" ,defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
                                            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        return topicService.getAllTopics(page, size, sort, direction);

    }

}
