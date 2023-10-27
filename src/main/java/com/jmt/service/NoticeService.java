package com.jmt.service;

import com.jmt.common.PagingUtil;
import com.jmt.dto.NoticeDto;
import com.jmt.entity.Notice;
import com.jmt.entity.Qna;
import com.jmt.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
public class NoticeService {
    @Autowired
    NoticeRepository noticeRepository;

    //validate 하나 만들기
    private void validate(final Notice notice){
        if (notice == null) {
            log.warn("Entity is null");
            throw new RuntimeException("Entity is null");
        }
    }

    //create
    public List<Notice> create(Notice notice){
        validate(notice);
        System.out.println("notice.getNoticeTitle() = " + notice.getNoticeTitle());
        noticeRepository.save(notice);
        System.out.println("qnaRepository.save(qna) = " + noticeRepository.save(notice));
        return noticeRepository.findByMember_Userid(notice.getMember().getUserid());
    }

    //관리자용 read
    public List<Notice> readByUserId(Long userId){
        return noticeRepository.findByMember_Userid(userId);
    }

    public List<Notice> readByNoticeNum(Long noticeNum){
        return noticeRepository.findByNoticeNum(noticeNum);
    }

    public Notice readNoticeByNoticeNum(Long noticeNum){
        return noticeRepository.findNoticeByNoticeNum(noticeNum);
    }


    //일반 유저 read
    public List<Notice> readAll() {
        return noticeRepository.findAll();
    }

    //category read
    public List<Notice> readByCategory(String noticeCategory){
        return noticeRepository.findByNoticeCategory(noticeCategory);
    }


    //update
    public List<Notice> updateNotice(Notice notice){
        validate(notice);

        Notice upNotice = noticeRepository.findByNoticeId(notice.getNoticeId());
        //update set
        upNotice.setNoticeCategory(notice.getNoticeCategory());
        upNotice.setNoticeTitle(notice.getNoticeTitle());
        upNotice.setNoticeContent(notice.getNoticeContent());
        upNotice.setModDate(LocalDateTime.now());
        upNotice.setNoticeFileKey(notice.getNoticeFileKey());
        noticeRepository.save(notice);

        return noticeRepository.findByMember_Userid(notice.getMember().getUserid());
    }


    //delete
    public List<Notice> deleteNotice(Notice notice){
        validate(notice);

        noticeRepository.delete(notice);

        return noticeRepository.findByMember_Userid(notice.getMember().getUserid());
    }

    //paging을 이용한 noticelist 가져오기
    public PagingUtil<NoticeDto> getNoticeList(int page, int size){
        return null;
    }

//    @Transactional
//    public void createNotice(Notice entity) {
//        try{
//            log.info("====notice : " + entity);
//            noticeRepository.save(entity);
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//    }
}
