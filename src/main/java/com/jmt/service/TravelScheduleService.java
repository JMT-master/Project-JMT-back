package com.jmt.service;

import com.jmt.dto.DayFormatDto;
import com.jmt.dto.TravelScheduleDto;
import com.jmt.entity.DayFormatEntity;
import com.jmt.entity.Member;
import com.jmt.entity.TravelScheduleEntity;
import com.jmt.repository.DayFormatRepository;
import com.jmt.repository.MemberRepository;
import com.jmt.repository.TravelScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelScheduleService {

    private final TravelScheduleRepository travelScheduleRepository;
    public TravelScheduleDto scheduleSave(TravelScheduleDto dto){

        TravelScheduleEntity save = travelScheduleRepository.save(TravelScheduleDto.toEntity(dto));

        return TravelScheduleDto.toDto(save);
    }

    public TravelScheduleDto scheduleSelect(TravelScheduleDto dto){

        TravelScheduleEntity select = travelScheduleRepository.findByTravelId(dto.getTravelId());

        return TravelScheduleDto.toDto(select);
    }


    //pdf 만들기 위한 travelId로 가져오기
    public TravelScheduleDto selectByTravelId(String travelId){
        System.out.println("travelId = " + travelId);
        TravelScheduleEntity travelScheduleEntity = travelScheduleRepository.findByTravelId(travelId);
        System.out.println("travelScheduleEntity = " + travelScheduleEntity);
        return TravelScheduleDto.toDto(travelScheduleEntity);
    }

    //마이페이지에 나의 일정 조회쿼리
    public List<TravelScheduleDto> selectMyTravelScehdule(String userId){

        String userId1 = userId;
        List<TravelScheduleEntity> select = travelScheduleRepository.selectMyTravelScehdule(userId,userId1);

        List<TravelScheduleDto> result = select.stream().map(TravelScheduleDto::toDto).collect(Collectors.toList());

        return result;
    }
    //테마페이지에 일정여부를 공개로 선택한 모든 일정의 조회쿼리
    public List<TravelScheduleDto> selectTravelSchedule(String userId){

        List<TravelScheduleEntity> select = travelScheduleRepository.selectTravelSchedule(userId);

        List<TravelScheduleDto> result = select.stream().map(TravelScheduleDto::toDto).collect(Collectors.toList());

        return result;
    }

}
