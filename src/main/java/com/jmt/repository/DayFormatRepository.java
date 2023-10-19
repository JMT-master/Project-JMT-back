package com.jmt.repository;

import com.jmt.entity.DayFormatEntity;
import com.jmt.entity.TravelScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DayFormatRepository extends JpaRepository<DayFormatEntity,String> {

    public TravelScheduleEntity findByTravelUserid(String userid);

}
