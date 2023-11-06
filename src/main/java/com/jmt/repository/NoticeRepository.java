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

    @Query("select max(noticeIdx) from Notice")
    Optional<Long> getNoticeByMaxIdx();
}
