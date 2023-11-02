package com.jmt.service;

import com.jmt.dto.TravelScheduleDto;
import com.jmt.entity.Member;
import com.jmt.entity.TravelScheduleEntity;
import com.jmt.repository.DayFormatRepository;
import com.jmt.repository.MemberRepository;
import com.jmt.repository.TravelScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

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

        TravelScheduleEntity travelScheduleEntity = travelScheduleRepository.findByTravelId(travelId);

        return TravelScheduleDto.toDto(travelScheduleEntity);
    }

}
