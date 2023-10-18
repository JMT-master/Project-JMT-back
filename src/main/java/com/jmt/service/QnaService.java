package com.jmt.service;

import com.jmt.entity.QnaEntity;
import com.jmt.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaService {

    private final QnaRepository qnaRepository;

    //crud 다 해야함
    //근데 c, u ,d는 관리자만 read는 일반 유저도 qna 페이지에 들어가면 읽을 수 있어야함
    //카테고리가 주어졌을 때 우리는 그에 맞는 list를 호출해야하는 read가 필요하다.

    private void validate(final QnaEntity qnaEntity) {
        if (qnaEntity == null) {
            log.warn("Entity is null");
            throw new RuntimeException("Entity is null");
        }
    }

    //create부터
    public List<QnaEntity> create(QnaEntity qnaEntity){
        validate(qnaEntity);
        qnaRepository.save(qnaEntity);
        log.info("qna saved..?", qnaEntity.getId());
        //user는 관리자이다. 따라서 관리자 아이디로 create하고 난 뒤 전체 qna 글을 가져온다.
        //사실상 id가 다르다면 create 조차 불가능
        return qnaRepository.findByQnaUserId(qnaEntity.getMember().getUserid());
    }

    //관리자용 read
    public List<QnaEntity> readByUserId(String userId){
        return qnaRepository.findByQnaUserId(userId);
    }

    //일반 유저용 read
    public List<QnaEntity> read(){
        return qnaRepository.findAll();
    }

    //category를 주고 그에 맞는 list 호출
    public List<QnaEntity> readByCategory(String qnaCategory){
        return qnaRepository.findByQnaCategory(qnaCategory);
    }

    //update 문
    public List<QnaEntity> update(final QnaEntity qnaEntity){
        validate(qnaEntity);

        final Optional<QnaEntity> original = qnaRepository.findById(qnaEntity.getId());
        original.ifPresent(qna -> {
            qna.setQnaTitle(qnaEntity.getQnaTitle());
            qna.setQnaContent(qnaEntity.getQnaContent());
            qna.setModDate(LocalDateTime.now());
            qna.setQnaCategory(qnaEntity.getQnaCategory());
            qna.setQnaFileKey(qnaEntity.getQnaFileKey());
            //qna를 수정하는 건 view count를 올릴 필요가 없어서 가져오기만 해도 될듯..?
            qna.setQnaView(qnaEntity.getQnaView());
            qnaRepository.save(qna);
        });

        return readByUserId(qnaEntity.getMember().getUserid());
    }

    //delete문
    public List<QnaEntity> delete(final QnaEntity qnaEntity){
        validate(qnaEntity);

        try {
            qnaRepository.delete(qnaEntity);
        }catch (Exception e){
            log.error("delete 도중 error 발생...", qnaEntity.getId(), e);
            throw new RuntimeException("delete 도중 error 발생함..." + qnaEntity.getId());
        }

        return readByUserId(qnaEntity.getMember().getUserid());
    }

}
