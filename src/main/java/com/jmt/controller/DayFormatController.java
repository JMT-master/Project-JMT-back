package com.jmt.controller;

import com.jmt.dto.DayFormatDto;
import com.jmt.entity.DayFormatEntity;
import com.jmt.service.DayFormatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DayFormatController {

    private final DayFormatService dayFormatService;

    @GetMapping("/travelSchedule")
    public DayFormatDto dayFormatSave(@RequestBody DayFormatDto dto, int dayCount, int dayIndex){

        dayFormatService.dayFormatSave(dto,dayCount,dayIndex);

        return dto;
    }

}
