package com.sophia.service.business;

import com.sophia.entity.concrates.business.*;
import com.sophia.entity.concrates.user.User;
import com.sophia.entity.event.EntryAddedEvent;
import com.sophia.entity.event.TopicCreatedEvent;
import com.sophia.exception.NotFoundException;
import com.sophia.messages.ErrorMessages;
import com.sophia.messages.SuccessMessages;
import com.sophia.payload.mapper.business.EntryMapper;
import com.sophia.payload.request.business.entry.CreateEntryRequest;
import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.payload.response.business.topic.BasicTopicResponse;
import com.sophia.payload.response.business.topic.EntriesAndBasicResponseTransfer;
import com.sophia.payload.response.composite.CompositeEntryResponse;
import com.sophia.payload.response.wrapper.ResponseMessage;
import com.sophia.repository.business.DislikeRepository;
import com.sophia.repository.business.EntryRepository;
import com.sophia.repository.business.LikeRepository;
import com.sophia.repository.business.TagRepository;
import com.sophia.service.helper.MethodHelper;
import com.sophia.service.helper.PageableHelper;
import com.sophia.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final TopicService topicService;
    private final EntryMapper entryMapper;
    private final EntryRepository entryRepository;
    private final UserService userService;
    private final PageableHelper pageableHelper;
    private final ApplicationEventPublisher eventPublisher;
    private final TagRepository tagRepository;
    private final MethodHelper methodHelper;


    public CompositeEntryResponse getAllEntriesByTopic(Long topicId, int page, int size, String sort, String direction, HttpServletRequest request) {

        User user = userService.getUserByUsername((String) request.getAttribute("username"));
        Pageable pageable = pageableHelper.createPageableWithProperties(page, size, sort, direction);
        EntriesAndBasicResponseTransfer entriesAndBasicResponseTransfer = topicService.getEntriesAndBasicResponseByTopic(topicId);
        List<Entry> entries = entriesAndBasicResponseTransfer.getEntries();
        BasicTopicResponse basicTopicResponse = entriesAndBasicResponseTransfer.getTopic();

        Page<EntryResponse> entryResponses = new PageImpl<>(entries.stream()
                .map(entry -> entryMapper.mapEntryToEntryResponse(entry, user))
                .collect(Collectors.toList()), pageable, entries.size());

        return CompositeEntryResponse.builder()
                .entries(entryResponses)
                .topic(basicTopicResponse)
                .build();
    }
    public void saveEntryService(Entry entry) {
       entryRepository.save(entry);
    }
    public ResponseMessage saveEntry(CreateEntryRequest entryRequest, Long topicId, HttpServletRequest request) {
        User user = userService.getUserByUsername((String) request.getAttribute("username"));
        Topic topic = topicService.getTopicByIdService(topicId);

        List<String> tagNames = methodHelper.tagNameExtractor(entryRequest);
        
        Set<Tag> tags = tagNames.stream()
                        .map(name -> tagRepository.findByName(name)
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(name).build())))
                        .collect(Collectors.toSet());

        Entry entry = entryMapper.mapCreateEntryRequestToEntry(entryRequest,  user, topic, tags);

        entryRepository.save(entry);
        eventPublisher.publishEvent(new EntryAddedEvent(this, topicId));


        return ResponseMessage.builder()
                .message(SuccessMessages.ENTRY_SUCCESSFULLY_CREATED)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }


    private Entry isEntryExist(Long id) {
        return entryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ErrorMessages.ENTRY_NOT_FOUND, id)));
    }

    public Entry getEntryById(Long id) {
        return isEntryExist(id);
    }

    public void saveEntryByTopic(Topic topic, User user ,String content){
        System.out.println("*********************************************************");
        Entry entry = Entry.builder()
                .topic(topic)
                .author(user)
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .content(content)
                .build();
        entryRepository.save(entry);
    }
    public List<Long> getTopicIdsByTagNames(List<String> tagNames) {
        return null;
    }
    @Async
    @EventListener
    public void onTopicCreated(TopicCreatedEvent event) {
        System.out.println("2*******************");
        Long topicId = event.getTopicId();
        Topic topic = topicService.getTopicByIdService(topicId);
        User user = userService.getUserByUsername(event.getUsername());
        String content = event.getContent();
        saveEntryByTopic(topic, user, content);
    }
}
