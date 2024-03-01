package com.sophia.controller.business;

import com.sophia.payload.request.business.CreateEntryRequest;
import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.service.business.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/entry")
@RequiredArgsConstructor
public class EntryController {

    private final EntryService entryService;


    @GetMapping("/{id}")
    public Page<EntryResponse> getAllTopics(@PathVariable Long id,
                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
                                            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
                                            HttpServletRequest request) {
        return entryService.getAllEntries(id, page, size, sort, direction, request);

    }

    @PostMapping("/{id}/post")
    public void saveTopic(@PathVariable Long id, @RequestBody CreateEntryRequest entryRequest, HttpServletRequest request) {
        entryService.saveEntry(entryRequest, id, request);
    }
    @GetMapping("/{id}/{entryId}/likeEntry")
    public String likeEntry(@PathVariable Long id, @PathVariable Long entryId, HttpServletRequest request) {
        return entryService.likeEntry(id, entryId, request);
    }
    @GetMapping("/{topicId}/{entryId}/dislikeEntry")
    public String dislikeEntry(@PathVariable Long topicId, @PathVariable Long entryId, HttpServletRequest request) {
        return entryService.dislikeEntry(topicId, entryId, request);
    }
}