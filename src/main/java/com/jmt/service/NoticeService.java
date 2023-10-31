package com.jmt.service;

import com.jmt.entity.Notice;
import com.jmt.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository repository;

    @Transactional
    public void createNotice(Notice entity) {
        try{
            log.info("====notice : " + entity);
            repository.save(entity);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Transactional
    public Notice readNoticeIdx(Long idx){
        return repository.findByNoticeIdx(idx);
    }

    public Notice readNotice(String noticeId){
        return repository.findById(noticeId).get();
    }

    @Transactional
    public List<Notice> readAllNotice(){
        return repository.findAllByOrderByNoticeIdxDesc();
    }

    @Transactional
    public void deleteNotice(Notice notice){
        repository.delete(notice);
    }


}
