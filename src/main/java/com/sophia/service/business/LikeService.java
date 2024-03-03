package com.sophia.service.business;

import com.sophia.entity.concrates.business.Like;
import com.sophia.entity.concrates.user.User;
import com.sophia.exception.NotFoundException;
import com.sophia.messages.ErrorMessages;
import com.sophia.messages.SuccessMessages;
import com.sophia.payload.response.wrapper.BasicResponseMessage;
import com.sophia.repository.business.LikeRepository;
import com.sophia.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final EntryService entryService;

    public BasicResponseMessage likeEntry(Long entryId, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        User user = userService.getUserByUsername(username);

        if(likeRepository.existsByEntryIdAndUserId(entryId, user.getId())){
            Like like = likeRepository.findByEntryIdAndUserId(entryId, user.getId());
            likeRepository.delete(like);
           return BasicResponseMessage.builder()
                    .message(SuccessMessages.LIKE_SUCCESSFULLY_REMOVED)
                    .state(Boolean.FALSE)
                    .build();
        }
        likeRepository.save(Like.builder()
                .entry(entryService.getEntryById(entryId))
                .user(user)
                .build());
        return BasicResponseMessage.builder()
                .message(SuccessMessages.LIKE_SUCCESSFULLY_CREATED)
                .state(Boolean.TRUE)
                .build();

    }
    public BasicResponseMessage getLikeStateByEntryId(Long entryId, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        User user = userService.getUserByUsername(username);
        if (likeRepository.existsByEntryIdAndUserId(entryId, user.getId())) {
            return BasicResponseMessage.builder()
                    .message(SuccessMessages.LIKE_EXISTS)
                    .state(Boolean.TRUE)
                    .build();
        }
        return BasicResponseMessage.builder()
                .message(SuccessMessages.LIKE_NOT_EXIST)
                .state(Boolean.FALSE)
                .build();

    }

}
