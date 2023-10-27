package com.jmt.repository;

import com.jmt.entity.Notice;
import com.jmt.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, String> {

    Notice findByNoticeId(String noticeId);

    public List<Notice> findByMember_Userid(Long userid);

    @Query("select n from Notice n where n.noticeCategory = :noticeCategory")
    List<Notice> findByNoticeCategory(@Param("noticeCategory") String noticeCategory);

    List<Notice> findByNoticeNum(Long noticeNum);

    Notice findNoticeByNoticeNum(Long noticeNum);
}
