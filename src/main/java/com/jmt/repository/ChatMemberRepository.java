package com.jmt.repository;

import com.jmt.dto.ChatUserDto;
import com.jmt.entity.ChatUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemberRepository extends JpaRepository<ChatUserEntity, Long> {
}
