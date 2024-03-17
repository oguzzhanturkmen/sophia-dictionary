package com.sophia.repository.business;

import com.sophia.entity.concrates.business.ChannelTag;
import com.sophia.entity.concrates.business.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("SELECT t FROM Topic t WHERE  t.lastUpdateDate >= :lastWeek")
    Page<Topic> findTopicsWithRecentUpdates(LocalDateTime lastWeek, Pageable pageable);


    Page<Topic> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT t FROM Topic t JOIN t.channelTags tag WHERE tag.name IN :names")
    Page<Topic> findTopicsByChannelTags(@Param("names") String[] names, Pageable pageable);
}
