package com.jmt.service;
import com.jmt.dto.DayForm;
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

    public int dayFormatSave(List<DayFormatDto> dtoList){
//        List<DayFormatEntity> dayFormatEntityList = dtoList.stream()
//                .map(data -> {
//                    return DayFormatDto.toEntity(dtoList.get(0).getDayId(),dayForm,travelId);
//                }).collect(Collectors.toList());
//        List<DayFormatEntity> saveResult = dayFormatRepository.saveAll(dayFormatEntityList);
//        List<DayFormatDto> result = saveResult.stream().map(DayFormatDto::toDto).collect(Collectors.toList());
        int result = 1;

        try{
            TravelScheduleEntity travelId = travelScheduleRepository.findByTravelId(dtoList.get(0).getDayTravelId());
            DayForm dayForm = new DayForm();

            for(int i=0; i<dtoList.size(); i++){
                dayForm.setDayCount(dtoList.get(i).getDayCount());
                dayForm.setDayIndex(dtoList.get(i).getDayIndex());
                dayForm.setDayTitle(dtoList.get(i).getDayTitle());
                dayForm.setDayRegion1(dtoList.get(i).getDayRegion1());
                dayForm.setDayRegion2(dtoList.get(i).getDayRegion2());

                DayFormatEntity dayFormatEntity = DayFormatDto.toEntity(dtoList.get(0).getDayId(),dayForm,travelId);

                dayFormatRepository.save(dayFormatEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
            result = 0;
        }
        return result;
    }

    public int dayFormatDelete(int travelId){
        int result = 1;
        try {
            dayFormatRepository.delete(dayFormatRepository.findByDayTravelId(travelId));
            result = 0;
        } catch (Exception e) {
            e.printStackTrace();
            result = 1;
        }
        return result;
    }

}
