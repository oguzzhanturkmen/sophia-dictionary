package com.sophia.controller;

import com.sophia.payload.request.business.CreateTopicRequest;
import com.sophia.payload.response.business.topic.PostTopicResponse;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainScreenController {

    private final TopicService topicService;

    @GetMapping
    public Page<TopicResponse> getAllTopics(@RequestParam(value = "page" ,defaultValue = "1") int page,
                                            @RequestParam(value = "size", defaultValue = "16") int size,
                                            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
                                            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        return topicService.getAllTopics(page, size, sort, direction);

    }
    @PostMapping("/post")
    public ResponseEntity<PostTopicResponse> saveTopic(@RequestBody CreateTopicRequest topicRequest, HttpServletRequest request) {
        return topicService.saveTopic(topicRequest, request);
    }

}
