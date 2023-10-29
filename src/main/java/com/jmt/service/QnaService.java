package com.jmt.service;

import com.jmt.common.PagingInfo;
import com.jmt.common.PagingUtil;
import com.jmt.dto.QnaDto;
import com.jmt.entity.Qna;
import com.jmt.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class QnaService {

    private final QnaRepository qnaRepository;

    //crud 다 해야함
    //근데 c, u ,d는 관리자만 read는 일반 유저도 qna 페이지에 들어가면 읽을 수 있어야함
    //카테고리가 주어졌을 때 우리는 그에 맞는 list를 호출해야하는 read가 필요하다.

    private void validate(final Qna qna) {
        if (qna == null) {
            log.warn("Entity is null");
            throw new RuntimeException("Entity is null");
        }
    }

    //create부터
    public List<Qna> create(Qna qna){
        validate(qna);
        Qna save = qnaRepository.save(qna);
        System.out.println("save = " + save);
        log.info("qna saved..?", qna.getId());
        //user는 관리자이다. 따라서 관리자 아이디로 create하고 난 뒤 전체 qna 글을 가져온다.
        //사실상 id가 다르다면 create 조차 불가능
        return qnaRepository.findByMember_Userid(qna.getMember().getEmail());
    }

    //관리자용 read
    public List<Qna> readByUserId(String userId){
        return qnaRepository.findByMember_Userid(userId);
    }

    //qna 상세 페이지 들어가는 용 read
    public List<Qna> readByQnaListColNum(Long qnaNum){
        return qnaRepository.findByQnaNum(qnaNum);
    }

    //값 하나 가져오기
    public Qna readByQnaNum(Long qnaNum){
        return qnaRepository.findQnaByQnaNum(qnaNum);
    }

    //일반 유저용 read
    public List<Qna> read(){
        return qnaRepository.findAll();
    }

    //category를 주고 그에 맞는 list 호출
    public List<Qna> readByCategory(String qnaCategory){
        return qnaRepository.findByQnaCategory(qnaCategory);
    }

    //update 문
    public List<Qna> update(final Qna qnaEntity){
        validate(qnaEntity);

        final Optional<Qna> original = qnaRepository.findById(qnaEntity.getId());

//        original.set(akl)
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

        return readByUserId(qnaEntity.getMember().getEmail());
    }



    //delete문
    public List<Qna> delete(final Qna qna){
        validate(qna);
        try {
            qnaRepository.delete(qna);
        }catch (Exception e){
            log.error("delete 도중 error 발생...", qna.getId(), e);
            throw new RuntimeException("delete 도중 error 발생함..." + qna.getId());
        }
        return readByUserId(qna.getMember().getEmail());
    }

    //paging을 이용한 QnaList 가져오기
    public PagingUtil<QnaDto> getQnaList(int page, int size){
        PageRequest pageRequest = PageRequest.of(page -1, size, Sort.by(Sort.Order.desc("regDate")));
        Page<Qna> qnaPage = qnaRepository.findAll(pageRequest);

        List<QnaDto> qnaDtoList = qnaPage.getContent().stream()
                .map(QnaDto::new).collect(Collectors.toList());

        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setCurrentPage(qnaPage.getNumber()+1);
        pagingInfo.setPageSize(qnaPage.getSize());
        pagingInfo.setTotalPages(qnaPage.getTotalPages());
        pagingInfo.setTotalItems(qnaPage.getTotalElements());
        pagingInfo.setHasNext(qnaPage.hasNext());
        pagingInfo.setHasPrevious(qnaPage.hasPrevious());

        return new PagingUtil<>(qnaDtoList, pagingInfo);
    }

}
