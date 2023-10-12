package com.jmt.service;

import com.jmt.dto.NoticeDto;
import com.jmt.entity.Notice;
import com.jmt.repository.NoticeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeService {

    NoticeRepository repository;

    @Transactional
    public void createNotice(NoticeDto noticeDto) {
//        Notice notice = noticeDto.dtoToNotice();
        Notice notice = Notice.builder()
                .noticeTitle("테스트용")
                .noticeCategory("숙박")
                .noticeContent("가나다라마사바사아자차")
                .build();
        repository.save(notice);
    }
}
