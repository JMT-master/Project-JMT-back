package com.jmt.repository;

import com.jmt.entity.KnowledgeAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KnowledgeAnswerRepository extends JpaRepository<KnowledgeAnswerEntity,Long> {
    List<KnowledgeAnswerEntity> findByKnNumOrderByRegDateDesc(Long knNum);

    Optional<KnowledgeAnswerEntity> findByKnNumAndAnswerWriterAndContentAndModDate(Long knNum, String answerWriter, String content, LocalDateTime modDate);

    List<KnowledgeAnswerEntity> findByKnNum(Long knNum);

    Optional<KnowledgeAnswerEntity> findByIdAndAnswerWriter(Long id, String answerWriter);
}
