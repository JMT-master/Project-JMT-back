package com.jmt.controller;

import com.jmt.dto.TravelScheduleDto;
import com.jmt.entity.TravelScheduleEntity;
import com.jmt.service.TravelScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travel")
@RequiredArgsConstructor
public class TravelScheduleController {

    private final TravelScheduleService travelScheduleService;

    @PostMapping("/saveSchedule")
    public ResponseEntity<String> scheduleSave(@RequestBody TravelScheduleDto dto){

        travelScheduleService.scheduleSave(dto);

        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/selectSchedule")
    public ResponseEntity<TravelScheduleDto> scheduleSelect(TravelScheduleDto dto){

        TravelScheduleDto travelDto =  travelScheduleService.scheduleSelect(dto);

        return ResponseEntity.ok().body(travelDto);
    }

}
