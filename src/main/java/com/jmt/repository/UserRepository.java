package com.jmt.repository;

import com.jmt.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserRepository  extends JpaRepository<CustomUser, Long> {

    User getByUserid(String userid);
}
