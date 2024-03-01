package com.sophia.service.business;

import com.sophia.entity.concrates.business.Entry;
import com.sophia.entity.concrates.business.Topic;
import com.sophia.entity.concrates.user.User;
import com.sophia.messages.Messages;
import com.sophia.payload.request.business.CreateTopicRequest;
import com.sophia.payload.response.business.topic.PostTopicResponse;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.payload.mapper.business.TopicMapper;
import com.sophia.repository.business.EntryRepository;
import com.sophia.repository.business.TopicRepository;
import com.sophia.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final UserService userService;
    private final EntryRepository entryRepository;


    public Page<TopicResponse> getAllTopics(int page, int size, String sort, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (direction.equals("DESC")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return topicRepository.findAll(pageable)
                .map(topicMapper::mapToResponse);

    }

    public Topic getTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Messages.TOPIC_NOT_FOUND + id));
    }

    public ResponseEntity<PostTopicResponse> saveTopic(CreateTopicRequest topicRequest , HttpServletRequest request) {
        User user = userService.getUserByUsername(request.getAttribute("username").toString());

        Topic topic = new Topic();
        topic.setName(topicRequest.getTopicName());
        topic.setAuthor(user);
        topic.setCreateDate(LocalDateTime.now());
        topic.setLastUpdateDate(LocalDateTime.now());




        Topic savedTopic = topicRepository.save(topic);

        Entry entry = new Entry();
        entry.setTopic(topic);
        entry.setAuthor(user);
        entry.setCreationDate(LocalDateTime.now());
        entry.setContent(topicRequest.getContent());

        entryRepository.save(entry);

        PostTopicResponse postTopicResponse = new PostTopicResponse();
        postTopicResponse.setTopicId(savedTopic.getId());
        postTopicResponse.setTopicName(savedTopic.getName());




        return ResponseEntity.ok(postTopicResponse);




    }
}
