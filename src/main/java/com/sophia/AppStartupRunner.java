package com.sophia;

import com.sophia.entity.concrates.business.ChannelTag;
import com.sophia.entity.enums.ChannelTags;
import com.sophia.repository.business.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppStartupRunner implements CommandLineRunner {


    private final ChannelRepository channelRepository;

    @Override
    public void run(String... args) throws Exception {
        for (ChannelTags channelTag : ChannelTags.values()) {
            ChannelTag channelEntity = ChannelTag.builder()
                    .name(channelTag.getName())
                    .description(channelTag.getExplanation())
                    .build();

            channelRepository.findByName(channelTag.getName()).orElseGet(() -> channelRepository.save(channelEntity));        }


    }
}

