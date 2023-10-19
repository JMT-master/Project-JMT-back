package com.jmt.controller;

import com.jmt.dto.TravelScheduleDto;
import com.jmt.entity.TravelScheduleEntity;
import com.jmt.service.TravelScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TravelScheduleController {

    private final TravelScheduleService travelScheduleService;

    @GetMapping("/selectSchedule")
    public TravelScheduleDto scheduleSave(@RequestBody TravelScheduleDto dto){

        travelScheduleService.scheduleSave(dto);

        return dto;
    }

}
