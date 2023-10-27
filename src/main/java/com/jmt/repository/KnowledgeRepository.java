package com.jmt.repository;

import com.jmt.entity.KnowledgeEntity;
import com.jmt.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KnowledgeRepository extends JpaRepository<KnowledgeEntity, Long> {
    Optional<KnowledgeEntity> findByTitle(String title);

    List<KnowledgeEntity> findByNum(Long num);

    List<KnowledgeEntity> findByUseridAndNum(Member userid, Long num);

    // Primary Key가 없어서 되지 않음
//    @Query("select max(num), title, content, category, view " +
//            "from KnowledgeEntity " +
//            "group by title, content, category, view")
//    List<KnowledgeEntity> distinctBynum();

    @Query("select distinct num from KnowledgeEntity")
    List<Long> distinctBynum();

    @Query("select count(distinct num) from KnowledgeEntity")
    Long countByNum();

}
