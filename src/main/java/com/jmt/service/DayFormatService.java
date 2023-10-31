package com.jmt.service;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public int dayFormatSave(Map<String,Object> dtoList,String id){


//            List<DayFormatEntity> dayFormatEntityList = dtoList1.stream()
//                    .map(data -> {
//                        return DayFormatDto.toEntity(dtoList1.get(0).getDayId(),dayForm,travelId);
//                    }).collect(Collectors.toList());
//            List<DayFormatEntity> saveResult = dayFormatRepository.saveAll(dayFormatEntityList);
//            List<DayFormatDto> result = saveResult.stream().map(DayFormatDto::toDto).collect(Collectors.toList());

        int result = 1;

        TravelScheduleEntity travelId = travelScheduleRepository.findByTravelId(id);

        try{
            List<DayFormatDto> dtoListForm1 = new ArrayList<>();
            List<DayFormatDto> dtoListForm2 = new ArrayList<>();

            List<DayFormatDto> dtoFinalForm1 = new ArrayList<>();
//            DayFormatDto dto = new DayFormatDto();
            List<Map<String, Object>> dtoList1 = (List<Map<String, Object>>) dtoList.get("dtoList1");
            List<Map<String, Object>> dtoList2 = (List<Map<String, Object>>) dtoList.get("dtoList2");

            if (dtoList1 != null && !dtoList1.isEmpty()) {
                for(int i=0; i<dtoList1.size(); i++){
//                    dto.setDayImage((String) dtoList1.get(i).get("dayImage"));
                    dtoListForm1.add((DayFormatDto) dtoList1.get(i).get("dayImage"));
                    dtoListForm1.add((DayFormatDto) dtoList1.get(i).get("dayTitle"));
                    dtoListForm1.add((DayFormatDto) dtoList1.get(i).get("dayRegion1"));
                    dtoListForm1.add((DayFormatDto) dtoList1.get(i).get("dayRegion2"));
                    dtoListForm1.add((DayFormatDto) dtoList1.get(i).get("dayCount"));
                    dtoListForm1.add((DayFormatDto) dtoList1.get(i).get("dayCount"));
                    dtoListForm1.add((DayFormatDto) dtoList1.get(i).get("dayIndex"));
                    DayFormatEntity dayFormatEntity1 =  DayFormatDto.toEntity(dtoListForm1.get(i),travelId);
                    dayFormatRepository.save(dayFormatEntity1);
                }
            } else {
                System.out.println("에러에러 dtoList1안에 값이 없어");
            }
            if (dtoList2 != null && !dtoList2.isEmpty()) {
                for(int i=0; i<dtoList2.size(); i++){
                    dtoListForm2.add((DayFormatDto) dtoList2.get(i).get("dayImage"));
                    dtoListForm2.add((DayFormatDto) dtoList2.get(i).get("dayTitle"));
                    dtoListForm2.add((DayFormatDto) dtoList2.get(i).get("dayRegion1"));
                    dtoListForm2.add((DayFormatDto) dtoList2.get(i).get("dayRegion2"));
                    dtoListForm2.add((DayFormatDto) dtoList2.get(i).get("dayCount"));
                    dtoListForm2.add((DayFormatDto) dtoList2.get(i).get("dayCount"));
                    dtoListForm2.add((DayFormatDto) dtoList2.get(i).get("dayIndex"));
                    DayFormatEntity dayFormatEntity2 =  DayFormatDto.toEntity(dtoListForm2.get(i),travelId);
                    dayFormatRepository.save(dayFormatEntity2);
                }
            } else {
                System.out.println("에러에러 dtoList2안에 값이 없어");
            }


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
            result = 0;
        } catch (Exception e) {
            e.printStackTrace();
            result = 1;
        }
        return result;
    }

}
