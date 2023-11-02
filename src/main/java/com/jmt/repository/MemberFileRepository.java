package com.jmt.repository;

import com.jmt.entity.MemberFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberFileRepository extends JpaRepository<MemberFile, String> {
    List<MemberFile> findByFileInfo(String fileInfo);
}
