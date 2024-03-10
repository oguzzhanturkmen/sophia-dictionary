package com.sophia.controller.composite;

import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.payload.response.wrapper.ResponseMessage;
import com.sophia.service.business.TopicService;
import com.sophia.service.composite.SearchService;
import com.sophia.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final TopicService topicService;

    @GetMapping()
    public Page<?> search(@RequestParam String query,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "16") int size,
                                        @RequestParam(value = "sort", defaultValue = "lastUpdateDate") String sort,
                                        @RequestParam(value = "direction", defaultValue = "DESC") String direction
                                                                ) {

        return searchService.getSearchByKeyword(query, page);


    }

}
