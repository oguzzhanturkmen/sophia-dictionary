package com.sophia.service.composite;

import com.sophia.entity.concrates.business.Entry;
import com.sophia.entity.concrates.business.Tag;
import com.sophia.entity.concrates.business.Topic;
import com.sophia.payload.mapper.business.EntryMapper;
import com.sophia.payload.mapper.business.TopicMapper;
import com.sophia.payload.mapper.user.UserMapper;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.payload.response.wrapper.ResponseMessage;
import com.sophia.repository.business.EntryRepository;
import com.sophia.repository.business.TagRepository;
import com.sophia.repository.business.TopicRepository;
import com.sophia.repository.user.UserRepository;
import com.sophia.service.business.TopicService;
import com.sophia.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final PageableHelper pageableHelper;
    private final EntryRepository entryRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EntryMapper entryMapper;
    private final TagRepository tagRepository;

    public Page<?> getSearchByKeyword(String query, int page) {
        Pageable pageable ;
        System.out.println("******************************************" + query);

        if (query.startsWith("@")){
            pageable = pageableHelper.createPageableWithProperties(page, 16, "username", "DESC");
            return userRepository.findByUsernameContainingIgnoreCase(query.substring(1), pageable)
                    .map(userMapper::mapUserToUserFollowersResponse);
        }
        else if (query.startsWith("#")){
            Tag tag = tagRepository.findByName(query.substring(1)).orElseThrow(() -> new RuntimeException("Tag not found"));
            pageable = pageableHelper.createPageableWithProperties(page, 16, "lastUpdateDate", "DESC");
            Page<Entry> entryPage = entryRepository.findByTagsNameIncludingIgnoreCase(tag.getName(), pageable);

            List<TopicResponse> distinctTopicResponses = entryPage.getContent().stream()
                    .map(entry -> topicMapper.mapToTopicResponse(entry.getTopic()))
                    .distinct()
                    .collect(Collectors.toList());

            return new PageImpl<>(distinctTopicResponses, pageable, distinctTopicResponses.size());



        }
        else {
            pageable = pageableHelper.createPageableWithProperties(page, 16, "lastUpdateDate", "DESC");
            return topicRepository.findByNameContainingIgnoreCase(query, pageable)
                    .map(topicMapper::mapToTopicResponse);
        }
    }
}
