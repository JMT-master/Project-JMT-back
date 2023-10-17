package com.jmt.service;

import com.jmt.entity.Notice;
import com.jmt.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class NoticeService {
    @Autowired
    NoticeRepository repository;

    @Transactional
    public void createNotice(Notice entity) {
        try{
            log.info("====notice : " + entity);
            repository.save(entity);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
