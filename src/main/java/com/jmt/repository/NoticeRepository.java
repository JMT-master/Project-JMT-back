package com.jmt.repository;

import com.jmt.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    public List<Notice> findByMember(String userid);

}
