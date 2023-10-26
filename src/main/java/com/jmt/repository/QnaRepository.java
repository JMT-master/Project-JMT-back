package com.jmt.repository;

import com.jmt.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QnaRepository extends JpaRepository<Qna, String> {

    //qna id로 qna글 하나를 가져오는 method
    Optional<Qna> findById(String qnaId);

    //userId(관리자 아이디)로 전체 qna 가져오기
    List<Qna> findByMember_Userid(String userId);

    //category를 바탕으로 qna list를 가져오는 method
    @Query("select q from Qna q where q.qnaCategory = :qnaCategory")
    List<Qna> findByQnaCategory(@Param("qnaCategory") String qnaCategory);

    //qnaColNum으로 하나 가져오기
    List<Qna> findByQnaNum(Long qnaNum);

    Qna findQnaByQnaNum(Long qnaNum);
}
