package com.sophia.controller.business;

import com.sophia.payload.request.business.entry.CreateEntryRequest;
import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.payload.response.composite.CompositeEntryResponse;
import com.sophia.payload.response.wrapper.BasicResponseMessage;
import com.sophia.payload.response.wrapper.ResponseMessage;
import com.sophia.service.business.DislikeService;
import com.sophia.service.business.EntryService;
import com.sophia.service.business.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/entry")
@RequiredArgsConstructor
public class EntryController {

    private final EntryService entryService;
    private final LikeService likeService;
    private final DislikeService dislikeService;


    @GetMapping("/{topicId}")
    public CompositeEntryResponse getAllEntriesByTopic(@PathVariable Long topicId,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "25") int size,
                                                       @RequestParam(value = "sort", defaultValue = "lastUpdateDate") String sort,
                                                       @RequestParam(value = "direction", defaultValue = "DESC") String direction,
                                                       HttpServletRequest request) {
        return entryService.getAllEntriesByTopic(topicId, page, size, sort, direction, request);

    }

    @PostMapping("/{topicId}/post")
    public ResponseMessage saveEntry(@PathVariable Long topicId, @RequestBody CreateEntryRequest entryRequest, HttpServletRequest request) {
        return entryService.saveEntry(entryRequest, topicId, request);
    }
    @GetMapping("/{entryId}/likeEntry")
    public BasicResponseMessage likeEntry(@PathVariable Long entryId, HttpServletRequest request) {
        return likeService.likeEntry(entryId, request);
    }
    @GetMapping("/{entryId}/dislikeEntry")
    public BasicResponseMessage dislikeEntry( @PathVariable Long entryId, HttpServletRequest request) {
        return dislikeService.dislikeEntry(entryId, request);
    }
}
