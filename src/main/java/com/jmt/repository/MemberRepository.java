package com.jmt.repository;

import com.jmt.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {

    Member findByUserid(String userid);
    Member findByUsername(String username);
    Member findByUsernameAndPhone(String username, String phone);
}
