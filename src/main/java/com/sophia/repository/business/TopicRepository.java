package com.sophia.repository.business;

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
}
