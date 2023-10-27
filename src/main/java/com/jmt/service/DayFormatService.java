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

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DayFormatService {

    private final TravelScheduleRepository travelScheduleRepository;

    private final DayFormatRepository dayFormatRepository;

    public List<DayFormatDto> dayFormatSelect1(String userid,int travleId){

        List<DayFormatEntity> select = dayFormatRepository.dayFormatSelect1(userid,travleId);

        List<DayFormatDto> result = select.stream().map(DayFormatDto::toDto).collect(Collectors.toList());
        return result;
    }

    public List<DayFormatDto> dayFormatSelect2(String userid,int travleId){

        List<DayFormatEntity> select = dayFormatRepository.dayFormatSelect2(userid,travleId);

        List<DayFormatDto> result = select.stream().map(DayFormatDto::toDto).collect(Collectors.toList());
        return result;
    }

    public List<DayFormatDto> dayFormatSelect3(String userid,int travleId){

        List<DayFormatEntity> select = dayFormatRepository.dayFormatSelect3(userid,travleId);

        List<DayFormatDto> result = select.stream().map(DayFormatDto::toDto).collect(Collectors.toList());

        return result;
    }

    public List<DayFormatDto> dayFormatSave(List<DayFormatDto> dtoList, int travelId){

        List<DayFormatEntity> dayFormatEntityList = dtoList.stream()
                .map(data -> {
                    data.setDayTravelId(travelId);
                    return DayFormatDto.toEntity(data);
                }).collect(Collectors.toList());

        List<DayFormatEntity> saveResult = dayFormatRepository.saveAll(dayFormatEntityList);

        List<DayFormatDto> result = saveResult.stream().map(DayFormatDto::toDto).collect(Collectors.toList());

        return result;
    }

    public int dayFormatDelete(int travelId){
        int result = 1;
        try {
            dayFormatRepository.delete(dayFormatRepository.findByTravelId(travelId));
            result = 0;
        } catch (Exception e) {
            e.printStackTrace();
            result = 1;
        }
        return result;
    }

}
