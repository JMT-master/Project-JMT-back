package com.jmt.repository;

import com.jmt.entity.KnowledgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KnowledgeRepository extends JpaRepository<KnowledgeEntity, Long> {
    Optional<KnowledgeEntity> findByTitle(String title);

    @Query("select distinct count(num) from KnowledgeEntity")
    Long countByNum();

}
