package com.jmt.repository;

import com.jmt.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {

    Member findByUserid(String userid);
    Optional<Member> findByEmail(String email);
    boolean existsByEmailAndSocialYn(String email, String socailYn);
    Member findByUsername(String username);
    Member findByUsernameAndPhone(String username, String phone);

    Optional<Member> findByEmailAndSocialYn(String email, String socialYn);

}
