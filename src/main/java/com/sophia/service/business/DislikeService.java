package com.sophia.service.business;

import com.sophia.entity.concrates.business.Dislike;
import com.sophia.entity.concrates.business.Like;
import com.sophia.entity.concrates.user.User;
import com.sophia.messages.SuccessMessages;
import com.sophia.payload.response.wrapper.BasicResponseMessage;
import com.sophia.repository.business.DislikeRepository;
import com.sophia.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class DislikeService {

    private final DislikeRepository dislikeRepository;
    private final UserService userService;
    private final EntryService entryService;

    public BasicResponseMessage dislikeEntry(Long entryId, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        User user = userService.getUserByUsername(username);

        if(dislikeRepository.existsByEntryIdAndUserId(entryId, user.getId())){
            Dislike dislike = dislikeRepository.findByEntryIdAndUserId(entryId, user.getId());
            dislikeRepository.delete(dislike);
            return BasicResponseMessage.builder()
                    .message(SuccessMessages.DISLIKE_SUCCESSFULLY_REMOVED)
                    .state(Boolean.FALSE)
                    .build();
        }
        dislikeRepository.save(Dislike.builder()
                .entry(entryService.getEntryById(entryId))
                .user(user)
                .build());
        return BasicResponseMessage.builder()
                .message(SuccessMessages.DISLIKE_SUCCESSFULLY_CREATED)
                .state(Boolean.TRUE)
                .build();

    }
    public BasicResponseMessage getDislikeStateByEntryId(Long entryId, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        User user = userService.getUserByUsername(username);
        if(dislikeRepository.existsByEntryIdAndUserId(entryId, user.getId())){
            return BasicResponseMessage.builder()
                    .message(SuccessMessages.DISLIKE_EXISTS)
                    .state(Boolean.TRUE)
                    .build();
        }
        return BasicResponseMessage.builder()
                .message(SuccessMessages.DISLIKE_NOT_EXIST)
                .state(Boolean.FALSE)
                .build();
    }
}
