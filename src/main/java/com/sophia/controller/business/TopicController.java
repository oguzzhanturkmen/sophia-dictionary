package com.sophia.controller.business;

import com.sophia.payload.request.business.topic.CreateTopicRequest;
import com.sophia.payload.response.abstracts.BaseTopicResponse;
import com.sophia.payload.response.business.topic.BasicTopicResponse;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.payload.response.wrapper.ResponseMessage;
import com.sophia.service.business.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;
    @GetMapping
    public Page<TopicResponse> getAllTopics(@RequestParam(value = "page" ,defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "16") int size,
                                                @RequestParam(value = "sort", defaultValue = "lastUpdateDate") String sort,
                                                @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        return topicService.getAllTopics(page, size, sort, direction);

    }
    @PostMapping("/post")
    public ResponseMessage<BasicTopicResponse> saveTopic(@RequestBody @Valid CreateTopicRequest topicRequest, HttpServletRequest request) {
        return topicService.saveTopic(topicRequest, request);
    }


    @GetMapping("/trending")
    public Page<TopicResponse> getTrendingTopics(@RequestParam(value = "page" ,defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "16") int size,
                                                @RequestParam(value = "sort", defaultValue = "lastUpdateDate") String sort,
                                                @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        return topicService.getTrendingTopics( page, size, sort, direction);
    }
    @GetMapping("/getTopicsByTags")
    public Page<TopicResponse> getTopicsByTags(@RequestParam(value = "page" ,defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "16") int size,
                                              @RequestParam(value = "sort", defaultValue = "lastUpdateDate") String sort,
                                              @RequestParam(value = "direction", defaultValue = "DESC") String direction,
                                              @RequestParam(value = "tags") String[] tags) {
        return topicService.getTopicsByTags(page, size, sort, direction, tags);
    }


}
