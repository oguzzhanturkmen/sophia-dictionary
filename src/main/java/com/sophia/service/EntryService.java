package com.sophia.service;

import com.sophia.entity.concrates.business.Dislike;
import com.sophia.entity.concrates.business.Entry;
import com.sophia.entity.concrates.business.Like;
import com.sophia.entity.concrates.business.Topic;
import com.sophia.entity.concrates.user.User;
import com.sophia.mapper.EntryMapper;
import com.sophia.payload.request.business.CreateEntryRequest;
import com.sophia.payload.response.business.entry.EntryResponse;
import com.sophia.payload.response.business.topic.TopicResponse;
import com.sophia.repository.DislikeRepository;
import com.sophia.repository.EntryRepository;
import com.sophia.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final TopicService topicService;
    private final EntryMapper entryMapper;
    private final EntryRepository entryRepository;
    private final UserService userService;
    private final DislikeRepository dislikeRepository;
    private final LikeRepository likeRepository;


    public Page<EntryResponse> getAllEntries(Long id, int page, int size, String sort, String direction, HttpServletRequest request) {

        //************* Problem with sorting *********
        Topic topic = topicService.getTopicById(id);
        List<Entry> entries = topic.getEntries();
        User user = new User();
        boolean isLiked;
        boolean isDisliked;
        if (request.getAttribute("username") != null) {
            String username = (String) request.getAttribute("username").toString();
            user = userService.getUserByUsername(username);



        }


        User finalUser = user;
        List<EntryResponse> entryResponses = entries.stream()
                .map(entry -> entryMapper.mapToResponse(entry, finalUser))
                .collect(Collectors.toList());

        Page<EntryResponse> entryResponsePage = new PageImpl<>(entryResponses, PageRequest.of(page, size).withSort(Sort.by(sort).ascending()), entries.size());
        if (Objects.equals(direction, "DESC")) {
            entryResponsePage = new PageImpl<>
                    (entryResponses, PageRequest.of(page, size).withSort(Sort.by(sort).descending()), entries.size());
        }

        return entryResponsePage;
    }
    public void saveEntry(Entry entry) {
       entryRepository.save(entry);
    }
    public void saveEntry(CreateEntryRequest entryRequest, Long id, HttpServletRequest request) {
        User user = userService.getUserByUsername(request.getAttribute("username").toString());

        Entry entry = new Entry();
        entry.setTopic(topicService.getTopicById(id));
        entry.setContent(entryRequest.getContent());
        entry.setCreationDate(LocalDateTime.now());
        entry.setAuthor(user);
        saveEntry(entry);



    }

    public String likeEntry(Long topicId, Long entryId, HttpServletRequest request) {
        String username = (String) request.getAttribute("username").toString();
        User user = userService.getUserByUsername(username);

        if(likeRepository.existsByEntryIdAndUserId(entryId, user.getId())){
            Like like = likeRepository.findByEntryIdAndUserId(entryId, user.getId());
            likeRepository.delete(like);
            return "false";
        }

            Like like = new Like();
            like.setEntry(entryRepository.findById(entryId).orElseThrow(() -> new IllegalArgumentException("Entry not found")));
            like.setUser(user);
            likeRepository.save(like);
            return "true";

    }


    public String dislikeEntry(Long topicId, Long entryId, HttpServletRequest request) {
        String username = (String) request.getAttribute("username").toString();
        User user = userService.getUserByUsername(username);
        if(dislikeRepository.existsByEntryIdAndUserId(entryId, user.getId())){
            Dislike dislike = dislikeRepository.findByEntryIdAndUserId(entryId, user.getId());
            dislikeRepository.delete(dislike);
            return "false";
        }

        Dislike dislike = new Dislike();
        dislike.setEntry(entryRepository.findById(entryId).orElseThrow(() -> new IllegalArgumentException("Entry not found")));
        dislike.setUser(user);
        dislikeRepository.save(dislike);
        return "true";




    }
}
