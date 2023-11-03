package com.jmt.repository;

import com.jmt.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishReopsitory extends JpaRepository<WishEntity,String> {

    WishEntity findByWishUserId(String userId);


}
