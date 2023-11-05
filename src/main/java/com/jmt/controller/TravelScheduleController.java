package com.jmt.controller;

import com.jmt.dto.ResponseDto;
import com.jmt.dto.TravelScheduleDto;
import com.jmt.entity.TravelScheduleEntity;
import com.jmt.service.TravelScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travel")
@RequiredArgsConstructor
public class TravelScheduleController {

    private final TravelScheduleService travelScheduleService;

    @PostMapping("/saveSchedule")
    public ResponseEntity<TravelScheduleDto> scheduleSave(@AuthenticationPrincipal String userid, @RequestBody TravelScheduleDto dto){
        dto.setTravelUserId(userid);
        TravelScheduleDto travelId = travelScheduleService.scheduleSave(dto);
        return ResponseEntity.ok().body(travelId);
    }

    @GetMapping("/selectSchedule")
    public ResponseEntity<TravelScheduleDto> scheduleSelect(TravelScheduleDto dto){

        TravelScheduleDto travelDto =  travelScheduleService.scheduleSelect(dto);

        return ResponseEntity.ok().body(travelDto);
    }

    //마이페이지에 나의 일정 조회쿼리
    @GetMapping("/myTtravelScheduleSelect")
    public ResponseEntity<ResponseDto> myTtravelScheduleSelect(@AuthenticationPrincipal String userid){

        List<TravelScheduleDto> dtoList;
        if(userid != null && userid != ""){
            dtoList = travelScheduleService.myTtravelScheduleSelect(userid);
        }
        else{
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .error("error")
                    .build());
        }
        return ResponseEntity.ok().body(ResponseDto.<TravelScheduleDto>builder()
                .error("success")
                .data(dtoList)
                .build());
    }

    //테마페이지에 일정여부를 공개로 선택한 모든 일정의 조회쿼리
    @GetMapping("/selectTravelSchedule")
    public ResponseEntity<ResponseDto> travelScheduleSelect(@AuthenticationPrincipal String userid){

        List<TravelScheduleDto> dtoList;
        if(userid != null && userid != ""){
            dtoList = travelScheduleService.myTtravelScheduleSelect(userid);
        }
        else{
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .error("error")
                    .build());
        }
        return ResponseEntity.ok().body(ResponseDto.<TravelScheduleDto>builder()
                .error("success")
                .data(dtoList)
                .build());
    }



}
