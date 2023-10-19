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

@Service
@Transactional
@RequiredArgsConstructor
public class TravelScheduleService {

    private final TravelScheduleRepository travelScheduleRepository;
    public TravelScheduleDto scheduleSave(TravelScheduleDto dto){

        TravelScheduleEntity save = travelScheduleRepository.save(TravelScheduleDto.toEntity(dto));

        return TravelScheduleDto.toDto(save);
    }

}
