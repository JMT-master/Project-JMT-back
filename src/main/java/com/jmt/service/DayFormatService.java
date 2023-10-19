package com.jmt.service;
import com.jmt.dto.DayFormatDto;
import com.jmt.dto.TravelScheduleDto;
import com.jmt.entity.DayFormatEntity;
import com.jmt.entity.TravelScheduleEntity;
import com.jmt.repository.DayFormatRepository;
import com.jmt.repository.TravelScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DayFormatService {

    private final TravelScheduleRepository travelScheduleRepository;

    private final DayFormatRepository dayFormatRepository;


    public DayFormatDto dayFormatSave(DayFormatDto dto, int dayCount, int dayIndex){

        TravelScheduleEntity travelScheduleEntity = travelScheduleRepository.findById(dto.getDayTravelId()).get();

        dto.setDayIndex(dayIndex);
        dto.setDayCount(dayCount);
        DayFormatEntity ff = DayFormatDto.toEntity(dto);
        ff.setDayTravelId(travelScheduleEntity);
        DayFormatEntity save = dayFormatRepository.save(ff);

        return DayFormatDto.toDto(save);
    }

}
