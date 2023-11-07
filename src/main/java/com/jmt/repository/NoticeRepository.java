package com.jmt.repository;

import com.jmt.entity.Notice;
import com.jmt.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, String> {

    public List<Notice> findByMember(String userid);

    public Notice findByNoticeIdx(Long idx);

    public Page<Notice> findAllByOrderByNoticeIdxDesc(Pageable pageable);

    public Page<Notice> findByNoticeTitleContaining(String result, Pageable pageable);
    public Page<Notice> findByNoticeContentContaining(String result, Pageable pageable);
    @Query("select max(noticeIdx) from Notice")
    Optional<Long> getNoticeByMaxIdx();

    //최신 날짜 순으로 2개 가져오기
    List<Notice> findTop2ByOrderByRegDateDesc();
}
