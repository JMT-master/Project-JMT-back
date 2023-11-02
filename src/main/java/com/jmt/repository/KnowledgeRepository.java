package com.jmt.repository;

import com.jmt.entity.KnowledgeEntity;
import com.jmt.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KnowledgeRepository extends JpaRepository<KnowledgeEntity, Long> {
    List<KnowledgeEntity> findByCategoryOrderByNumDesc(String category);

    Optional<KnowledgeEntity> findByNum(Long num);

    Optional<KnowledgeEntity> findByUseridAndNum(Member userid, Long num);

    List<KnowledgeEntity> findByTitleContaining(String title);
    List<KnowledgeEntity> findByContentContaining(String content);

    // Primary Key가 없어서 되지 않음
//    @Query("select max(num), title, content, category, view " +
//            "from KnowledgeEntity " +
//            "group by title, content, category, view")
//    List<KnowledgeEntity> distinctBynum();
    @Query("select distinct num from KnowledgeEntity order by num desc")
    List<Long> distinctBynum();

    @Query("select max(num) from KnowledgeEntity")
    Optional<Long> countByNum();

}
