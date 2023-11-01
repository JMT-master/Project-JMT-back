package com.jmt.service;
import com.jmt.dto.DayFormatDto;
import com.jmt.entity.DayFormatEntity;
import com.jmt.entity.TravelScheduleEntity;
import com.jmt.repository.DayFormatRepository;
import com.jmt.repository.TravelScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DayFormatService {

    private final TravelScheduleRepository travelScheduleRepository;

    private final DayFormatRepository dayFormatRepository;

    public List<DayFormatDto> dayFormatSelect1(String userid,String id){
        System.out.println("userid = " + userid);
        System.out.println("id = " + id);
        List<DayFormatEntity> select = dayFormatRepository.dayFormatSelect1(userid,id);
        System.out.println("select = " + select);
        List<DayFormatDto> result = select.stream().map(data -> DayFormatDto.toDto(data,id)).collect(Collectors.toList());
        System.out.println("result = " + result);
        return result;
    }

    public List<DayFormatDto> dayFormatSelect2(String userid,String id){

        List<DayFormatEntity> select = dayFormatRepository.dayFormatSelect2(userid,id);

        List<DayFormatDto> result = select.stream().map(data -> DayFormatDto.toDto(data,id)).collect(Collectors.toList());
        return result;
    }

    public List<DayFormatDto> dayFormatSelect3(String userid,String id){

        List<DayFormatEntity> select = dayFormatRepository.dayFormatSelect3(userid,id);

        List<DayFormatDto> result = select.stream().map(data -> DayFormatDto.toDto(data,id)).collect(Collectors.toList());

        return result;
    }

    public int dayFormatSave(List<DayFormatDto> dtoList,String id){
        int result = 1;
        try{
            TravelScheduleEntity travelId = travelScheduleRepository.findByTravelId(id);
            List<DayFormatEntity> dayFormatEntityList = dtoList.stream()
                    .map(data ->  DayFormatDto.toEntity(data,travelId)).collect(Collectors.toList());
            List<DayFormatEntity> saveResult = dayFormatRepository.saveAll(dayFormatEntityList);
//            List<DayFormatDto> result = saveResult.stream().map(DayFormatDto::toDto).collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
            result = 0;
        }
        return result;
    }

    public int dayFormatDelete(String travelId){
        int result = 1;
        try {
            dayFormatRepository.delete(dayFormatRepository.findByDayTravelId(travelId));
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
    }

}
