package com.jmt.repository;

import com.jmt.entity.DayFormatEntity;
import com.jmt.entity.TravelScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
public interface TravelScheduleRepository extends JpaRepository<TravelScheduleEntity, Integer> {

    TravelScheduleEntity findByTravelId(String id);

    //나의 일정 조회하는 쿼리
    @Query(value = "select a.travel_id," +
            "      a.travel_user_id," +
            "      a.travel_title," +
            "      a.travel_yn," +
            "      a.travel_pnum," +
            "      a.travel_start_time," +
            "      a.travel_end_time," +
            "      a.travel_start_date," +
            "      a.travel_end_date," +
            "      a.mod_date," +
            "      a.reg_date," +
            "      (select b.day_image " +
            "         from day_format b" +
            "         where b.day_user_id= ? " +
            "           and a.travel_id = b.day_travelid" +
            "      order by day_count, day_index limit 1) as day_image"+
            " from travel_schedule a" +
            " where a.travel_user_id = ? ",nativeQuery = true)
    List<TravelScheduleEntity> selectMyTravelScehdule(String userid,String userid1);

    @Query(value = "select a.travel_id," +
            "      a.travel_user_id," +
            "      a.travel_title," +
            "      a.travel_yn," +
            "      a.travel_pnum," +
            "      a.travel_start_time," +
            "      a.travel_end_time," +
            "      a.travel_start_date," +
            "      a.travel_end_date," +
            "      a.mod_date," +
            "      a.reg_date," +
            "      (select b.day_image " +
            "         from day_format b" +
            "        where a.travel_id = b.day_travelid" +
            "      order by day_count, day_index limit 1) as day_image"+
            " from travel_schedule a" +
            " where a.travel_yn = 'Y' ",nativeQuery = true)
    List<TravelScheduleEntity> selectTravelSchedule(String userid);

}
