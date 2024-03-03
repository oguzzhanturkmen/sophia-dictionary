package com.sophia.repository.business;

import com.sophia.entity.concrates.business.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByEntryIdAndUserId(Long entryId, Long userId);

    boolean existsByEntryIdAndUserId(Long entryId, Long userId);

    void deleteByEntryIdAndUserId(Long entryId, Long userId);
}
