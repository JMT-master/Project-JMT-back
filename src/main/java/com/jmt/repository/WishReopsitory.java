package com.jmt.repository;

import com.jmt.entity.TravelScheduleEntity;
import com.jmt.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishReopsitory extends JpaRepository<WishEntity,String> {

    WishEntity findByWishUserId(String userId);

    //찜한 여행지 조회 쿼리
    @Query(value = "select wish_id," +
            "              mod_date," +
            "              reg_date," +
            "              wish_api_id," +
            "              wish_gubun," +
            "              wish_img," +
            "              wish_title," +
            "              wish_user_id," +
            "              wish_travel_id," +
            "              address," +
            "              phoneno," +
            "              content," +
            "              tag" +
            "         from wish_list" +
            "        where wish_user_id = ?"+
            "          and wish_gubun = 'tdn' ",nativeQuery = true)
    List<WishEntity> wishTdnSelect(String userid);

    //찜한 여행일정 조회 쿼리
    @Query(value = "select b.wish_id," +
            "              b.mod_date," +
            "              b.reg_date," +
            "              b.wish_api_id," +
            "              b.wish_gubun," +
            "              b.wish_img," +
            "              b.wish_title," +
            "              b.wish_user_id," +
            "              b.wish_travel_id," +
            "              b.address," +
            "              b.phoneno," +
            "              b.content," +
            "              b.tag" +
            "         from travel_schedule a inner join wish_list b" +
            "              on a.travel_id = b.wish_travel_id" +
            "        where b.wish_user_id = ?" +
            "              and b.wish_gubun = 'tps'",nativeQuery = true)
    List<WishEntity> wishTpsSelect(String userid);



}