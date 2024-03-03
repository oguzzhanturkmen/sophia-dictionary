package com.sophia.service.business;

import com.sophia.entity.concrates.business.Entry;
import com.sophia.entity.concrates.business.Topic;
import com.sophia.entity.concrates.user.User;
import com.sophia.entity.event.EntryAddedEvent;
import com.sophia.entity.event.TopicCreatedEvent;
import com.sophia.exception.NotFoundException;
import com.sophia.messages.ErrorMessages;
import com.sophia.payload.request.business.topic.CreateTopicRequest;
import com.sophia.payload.response.abstracts.BaseTopicResponse;
import com.sophia.payload.mapper.business.TopicMapper;
import com.sophia.payload.response.business.topic.BasicTopicResponse;
import com.sophia.payload.response.business.topic.EntriesAndBasicResponseTransfer;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.payload.response.wrapper.ResponseMessage;
import com.sophia.repository.business.TopicRepository;
import com.sophia.service.helper.PageableHelper;
import com.sophia.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final UserService userService;
    private final PageableHelper pageableHelper;
    private final ApplicationEventPublisher eventPublisher;


    public Page<TopicResponse> getAllTopics(int page, int size, String sort, String direction) {
        Pageable pageable = pageableHelper.createPageableWithProperties(page, size, sort, direction);
        return topicRepository.findAll(pageable)
                .map(topicMapper::mapToTopicResponse);

    }

    public Topic getTopicByIdService(Long id) {
        return isTopicExist(id);
    }
    public TopicResponse getTopicById(Long id) {
        return topicMapper.mapToTopicResponse(isTopicExist(id));
    }

    public ResponseMessage<BasicTopicResponse> saveTopic(CreateTopicRequest topicRequest , HttpServletRequest request) {
        User user = userService.getUserByUsername(request.getAttribute("username").toString());
        Topic savedTopic = topicRepository.save(topicMapper.mapCreateTopicRequestToTopic(topicRequest, user));
        Long topicId = savedTopic.getId();
        eventPublisher.publishEvent(new TopicCreatedEvent(this, topicId, user.getUsername(), topicRequest.getContent()));
        //entryService.saveEntryByTopic(savedTopic ,user);

        return ResponseMessage.<BasicTopicResponse>builder()
                .object(topicMapper.mapToBasicTopicResponse(savedTopic))
                .message("Topic has been created successfully")
                .httpStatus(HttpStatus.CREATED)
                .build();

    }

    public Page<TopicResponse> getTrendingTopics( int page, int size, String sort, String direction) {
        //TODO: Implement a good way of sorting the topics by also the number of entries
        Pageable pageable = pageableHelper.createPageableWithProperties(page, size, sort, direction);
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);

        return topicRepository.findTopicsWithRecentUpdates(lastWeek, pageable).
                map(topicMapper::mapToTopicResponse);
    }

    private Topic isTopicExist(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ErrorMessages.TOPIC_NOT_FOUND, id)));
    }
    @Async
    @EventListener
    public void onEntryAdded(EntryAddedEvent event) {
        Long topicId = event.getTopicId();
        Topic topic = isTopicExist(topicId);
        updateTopicLastUpdateDate(topic);
    }
    public void updateTopicLastUpdateDate(Topic topic){
        topic.setLastUpdateDate(LocalDateTime.now());
        topicRepository.save(topic);
    }
    public EntriesAndBasicResponseTransfer getEntriesAndBasicResponseByTopic(Long topicId  ){
        Topic topic = isTopicExist(topicId);
        return EntriesAndBasicResponseTransfer.builder()
                .entries(topic.getEntries())
                .topic(topicMapper.mapToBasicTopicResponse(topic))
                .build();
    }

}
