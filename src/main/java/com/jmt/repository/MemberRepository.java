package com.jmt.repository;

import com.jmt.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {

    Member findByUsername(String username);
}
