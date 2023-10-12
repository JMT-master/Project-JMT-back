package com.jmt.qna.repository;

import com.jmt.qna.entity.QnaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QnaRepository extends JpaRepository<QnaEntity, String> {

    Optional<QnaEntity> findById(String qnaId);


}
