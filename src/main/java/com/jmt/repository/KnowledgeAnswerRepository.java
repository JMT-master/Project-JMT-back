package com.jmt.repository;

import com.jmt.entity.KnowledgeAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KnowledgeAnswerRepository extends JpaRepository<KnowledgeAnswerEntity,Long> {
    List<KnowledgeAnswerEntity> findByKnNumOrderByModDateDesc(Long knNum);

    Optional<KnowledgeAnswerEntity> findByKnNumAndAnswerWriterAndContent(Long knNum, String answerWriter, String content);
}
