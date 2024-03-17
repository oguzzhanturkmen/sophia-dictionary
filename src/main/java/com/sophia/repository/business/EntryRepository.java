package com.sophia.repository.business;

import com.sophia.entity.concrates.business.Entry;
import com.sophia.entity.concrates.business.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {


     @Query("SELECT e FROM Entry e JOIN e.tags t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :tagName, '%'))")
     Page<Entry> findByTagNameInclusive(@Param("tagName") String tagName, Pageable pageable);

     Page<Entry> findByTagsName(String tagName, Pageable pageable);


     Page<Entry> findByTags(Tag tag, Pageable pageable);

     @Query("SELECT DISTINCT e FROM Entry e JOIN e.tags t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :tagName, '%'))")
     Page<Entry> findByTagsNameIncludingIgnoreCase(@Param("tagName") String tagName, Pageable pageable);




}
