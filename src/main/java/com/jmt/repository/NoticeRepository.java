package com.jmt.repository;

import com.jmt.entity.Notice;
import com.jmt.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, String> {

    public List<Notice> findByMember(String userid);

    public Notice findByNoticeIdx(Long idx);
}
