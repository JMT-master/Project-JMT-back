package com.jmt.qna.service;

import com.jmt.qna.entity.QnaEntity;
import com.jmt.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaService {

    private final QnaRepository qnaRepository;

    //crud 다 해야함

    private void validate(final QnaEntity qnaEntity) {
        if (qnaEntity == null) {
            log.warn("Entity is null");
            throw new RuntimeException("Entity is null");
        }
    }

    //create부터
    private List<QnaEntity> create(QnaEntity qnaEntity){
        validate(qnaEntity);
        qnaRepository.save(qnaEntity);
        log.info("qna saved..?", qnaEntity.getId());
        //user는 관리자이다. 따라서 관리자 아이디로 create하고 난 뒤 전체 qna 글을 가져온다.
        //사실상 id가 다르다면 create 조차 불가능
        return qnaRepository.findByQnaUserId(qnaEntity.getQnaUserId());
    }

    //read
    private List<QnaEntity> read(String userId){
        return qnaRepository.findByQnaUserId(userId);
    }

}
