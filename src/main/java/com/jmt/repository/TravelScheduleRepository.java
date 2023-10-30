package com.jmt.repository;

import com.jmt.entity.TravelScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
public interface TravelScheduleRepository extends JpaRepository<TravelScheduleEntity, Integer> {

    TravelScheduleEntity findByTravelId(String id);

}
