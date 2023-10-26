package com.jmt.repository;

import com.jmt.entity.DayFormatEntity;
import com.jmt.entity.TravelScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DayFormatRepository extends JpaRepository<DayFormatEntity,String> {

    public TravelScheduleEntity findByTravelUserid(String userid);

    @Query(value = "select b.region1," +
            "      b.region2," +
            "      b.title," +
            "      b.day_travel_id," +
            "      b.day_index" +
            " from travelschedule a inner join dayformat b" +
            "   on a.travel_id = b.day_travel_id" +
            "where a.travel_userid = ? " +
            "  and b.day_count = 1" +
            "  and b.day_index is not null",nativeQuery = true)
    TravelScheduleEntity dayFormatSelect1(String userid);

    @Query(value = "select b.region1," +
            "      b.region2," +
            "      b.title," +
            "      b.day_travel_id," +
            "      b.day_index" +
            " from travelschedule a inner join dayformat b" +
            "   on a.travel_id = b.day_travel_id" +
            "where a.travel_userid = ? " +
            "  and b.day_count = 2" +
            "  and b.day_index is not null",nativeQuery = true)
    TravelScheduleEntity dayFormatSelect2(String userid);

    @Query(value = "select b.region1," +
            "      b.region2," +
            "      b.title," +
            "      b.day_travel_id," +
            "      b.day_index" +
            " from travelschedule a inner join dayformat b" +
            "   on a.travel_id = b.day_travel_id" +
            "where a.travel_userid = ? " +
            "  and b.day_count = 3" +
            "  and b.day_index is not null",nativeQuery = true)
    TravelScheduleEntity dayFormatSelect3(String userid);



}
