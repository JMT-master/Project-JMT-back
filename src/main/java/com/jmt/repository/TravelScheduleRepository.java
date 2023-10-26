package com.jmt.repository;

import com.jmt.entity.TravelScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TravelScheduleRepository extends JpaRepository<TravelScheduleEntity, String> {

    TravelScheduleEntity findByTravelid(String id);

}
