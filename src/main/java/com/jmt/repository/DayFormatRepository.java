package com.jmt.repository;

import com.jmt.entity.DayFormatEntity;
import com.jmt.entity.TravelScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DayFormatRepository extends JpaRepository<DayFormatEntity,String> {


    DayFormatEntity findByDayTravelId(String travelId);

    @Query(value = "select b.day_region1," +
            "      b.day_region2," +
            "      b.day_title," +
            "      b.day_travelid," +
            "      b.day_index" +
            " from travelschedule a inner join dayformat b" +
            "   on a.travel_id = b.day_travelid" +
            "where a.travel_user_id = ? " +
            "  and b.day_travelid = ?" +
            "  and b.day_count = 1" +
            "  and b.day_index is not null",nativeQuery = true)
    List<DayFormatEntity> dayFormatSelect1(String userid, int travelId);

    @Query(value = "select b.day_region1," +
            "      b.day_region2," +
            "      b.day_title," +
            "      b.day_travelid," +
            "      b.day_index" +
            " from travelschedule a inner join dayformat b" +
            "   on a.travel_id = b.day_travelid" +
            "where a.travel_user_id = ? " +
            "  and b.day_travelid = ?" +
            "  and b.day_count = 1" +
            "  and b.day_index is not null",nativeQuery = true)
    List<DayFormatEntity> dayFormatSelect2(String userid, int travelId);

    @Query(value = "select b.day_region1," +
            "      b.day_region2," +
            "      b.day_title," +
            "      b.day_travelid," +
            "      b.day_index" +
            " from travelschedule a inner join dayformat b" +
            "   on a.travel_id = b.day_travelid" +
            "where a.travel_user_id = ? " +
            "  and b.day_travelid = ?" +
            "  and b.day_count = 1" +
            "  and b.day_index is not null",nativeQuery = true)
    List<DayFormatEntity> dayFormatSelect3(String userid, int travelId);



}
