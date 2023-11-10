package com.jmt.repository;

import com.jmt.entity.DayFormatEntity;
import com.jmt.entity.TravelScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DayFormatRepository extends JpaRepository<DayFormatEntity,String> {

    List<DayFormatEntity> findByDayTravelId(TravelScheduleEntity travelId);

    DayFormatEntity deleteByDayTravelId(TravelScheduleEntity dayTravelId);

    DayFormatEntity findByDayId(String dayId);
    @Query(value = "select b.day_id," +
            "      b.day_travelid," +
            "      b.day_count," +
            "      b.day_index," +
            "      b.day_title," +
            "      b.day_region1,"+
            "      b.day_region2," +
            "      b.day_Image," +
            "      b.day_latitude," +
            "      b.day_longitude," +
            "      b.day_user_id" +
            " from travel_schedule a inner join day_format b" +
            "   on a.travel_id = b.day_travelid" +
            " where b.day_travelid = ?" +
            "  and b.day_count = 1" +
            "  and b.day_index is not null "+
            "order by b.day_index asc",nativeQuery = true)
    List<DayFormatEntity> dayFormatSelect1(String travelId);

    @Query(value = "select b.day_id," +
            "      b.day_travelid," +
            "      b.day_count," +
            "      b.day_index," +
            "      b.day_title," +
            "      b.day_region1,"+
            "      b.day_region2," +
            "      b.day_Image," +
            "      b.day_latitude," +
            "      b.day_longitude," +
            "      b.day_user_id" +
            " from travel_schedule a inner join day_format b" +
            "   on a.travel_id = b.day_travelid" +
            " where b.day_travelid = ?" +
            "  and b.day_count = 2" +
            "  and b.day_index is not null "+
            "ORDER BY b.day_index ASC",nativeQuery = true)
    List<DayFormatEntity> dayFormatSelect2(String travelId);

    @Query(value = "select b.day_id," +
            "      b.day_travelid," +
            "      b.day_count," +
            "      b.day_index," +
            "      b.day_title," +
            "      b.day_region1,"+
            "      b.day_region2," +
            "      b.day_Image," +
            "      b.day_latitude," +
            "      b.day_longitude," +
            "      b.day_user_id" +
            " from travel_schedule a inner join day_format b" +
            "   on a.travel_id = b.day_travelid" +
            " where a.travel_user_id = ? " +
            "  and b.day_travelid = ?" +
            "  and b.day_count = 3" +
            "  and b.day_index is not null "+
            "ORDER BY b.day_index ASC",nativeQuery = true)
    List<DayFormatEntity> dayFormatSelect3(String userid, String travelId);



}
