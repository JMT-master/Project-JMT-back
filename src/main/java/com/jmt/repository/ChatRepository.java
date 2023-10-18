package com.jmt.repository;

import com.jmt.dto.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatRoom, String> {



}
