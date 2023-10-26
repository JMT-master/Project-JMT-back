package com.jmt.repository;

import com.jmt.entity.MemberFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFileRepository extends JpaRepository<MemberFile, String> {
}
