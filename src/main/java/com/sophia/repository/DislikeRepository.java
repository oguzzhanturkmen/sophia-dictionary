package com.sophia.repository;

import com.sophia.entity.concrates.business.Dislike;
import com.sophia.entity.concrates.business.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DislikeRepository extends JpaRepository<Dislike, Long> {

    Dislike findByEntryIdAndUserId(Long entryId, Long userId);

    boolean existsByEntryIdAndUserId(Long entryId, Long userId);
}
