package com.jmt.repository;

import com.jmt.entity.DayFormatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DayFormatRepository extends JpaRepository<DayFormatEntity,String> {

    Optional<DayFormatEntity> findById(String dayId);

}
