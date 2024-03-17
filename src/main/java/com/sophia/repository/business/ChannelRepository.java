package com.sophia.repository.business;

import com.sophia.entity.concrates.business.ChannelTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<ChannelTag, Long> {
    Optional<ChannelTag> findByName(String name);

    List<ChannelTag> findTagsByName(String[] tags);
}
