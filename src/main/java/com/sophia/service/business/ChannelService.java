package com.sophia.service.business;

import com.sophia.payload.response.business.channel.ChannelResponse;
import com.sophia.repository.business.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;

    public List<ChannelResponse> getChannels (){
        return channelRepository.findAll().stream().map(channel -> ChannelResponse.builder()
                .name(channel.getName())
                .description(channel.getDescription())
                .build()).collect(Collectors.toList());
    }
}
