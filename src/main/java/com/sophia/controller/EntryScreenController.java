package com.sophia.controller;

import com.sophia.payload.request.business.CreateEntryRequest;
import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.service.EntryService;
import com.sophia.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class EntryScreenController {

    private final EntryService entryService;


    @GetMapping("/{id}")
    public Page<EntryResponse> getAllTopics(@PathVariable Long id,
                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
                                            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        return entryService.getAllEntries(id, page, size, sort, direction);

    }

    @PostMapping("/{id}/post")
    public void saveTopic(@PathVariable Long id, @RequestBody CreateEntryRequest entryRequest, HttpServletRequest request) {
        entryService.saveEntry(entryRequest, id, request);
    }
}
