package com.sophia.controller.business;

import com.sophia.payload.response.business.channel.ChannelResponse;
import com.sophia.service.business.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;



    @GetMapping("/getChannels")
    public List<ChannelResponse> getChannels (){
        return channelService.getChannels();
    }
}
