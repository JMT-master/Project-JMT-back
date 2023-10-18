package com.jmt.repository;

import com.jmt.entity.QnaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QnaRepository extends JpaRepository<QnaEntity, String> {

    //qna id로 qna글 하나를 가져오는 method
    Optional<QnaEntity> findById(String qnaId);

    //userId(관리자 아이디)로 전체 qna 가져오기
    List<QnaEntity> findByMember_Userid(String userId);

    //category를 바탕으로 qna list를 가져오는 method
    @Query("select q from QnaEntity q where q.qnaCategory = :qnaCategory")
    List<QnaEntity> findByQnaCategory(@Param("qnaCategory") String qnaCategory);


}
