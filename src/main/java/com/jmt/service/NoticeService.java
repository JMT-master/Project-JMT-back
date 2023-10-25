package com.jmt.service;

import com.jmt.entity.Notice;
import com.jmt.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Notice readNotice(Long idx){
        return repository.findByNoticeIdx(idx);
    }

    @Transactional
    public List<Notice> readAllNotice(){
        return repository.findAll();
    }

    @Transactional
    public void deleteNotice(Notice notice){
        repository.delete(notice);
    }


}
