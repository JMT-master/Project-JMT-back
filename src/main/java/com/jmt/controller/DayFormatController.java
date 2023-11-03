package com.jmt.controller;

import com.jmt.dto.DayFormatDto;
import com.jmt.dto.ResponseDto;
import com.jmt.entity.DayFormatEntity;
import com.jmt.service.DayFormatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/travel")
@RequiredArgsConstructor
public class DayFormatController {

    private final DayFormatService dayFormatService;

    @GetMapping("/dayFormatSelect1")
    public ResponseEntity<ResponseDto> dayFormatSelect1(@AuthenticationPrincipal String userid,@RequestParam(value = "id")String id) {
        List<DayFormatDto> dtoList;

        if (id != null && id != "") {
            dtoList = dayFormatService.dayFormatSelect1(userid, id);
        }
        else {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .error("error")
                    .build());
        }

        return ResponseEntity.ok().body(ResponseDto.<DayFormatDto>builder()
                .error("success")
                .data(dtoList)
                .build());
    }

    @GetMapping("/dayFormatSelect2")
    public ResponseEntity<ResponseDto> dayFormatSelect2(@AuthenticationPrincipal String userid,@RequestParam(value = "id")String id){

        List<DayFormatDto> dtoList;

        if (id != null && id != "") {
            dtoList = dayFormatService.dayFormatSelect2(userid, id);
        }
        else {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .error("error")
                    .build());
        }

        return ResponseEntity.ok().body(ResponseDto.<DayFormatDto>builder()
                .error("success")
                .data(dtoList)
                .build());
    }

    @GetMapping("/dayFormatSelect3")
    public ResponseEntity<ResponseDto> dayFormatSelect3(@AuthenticationPrincipal String userid,@RequestParam(value = "id")String id){

        List<DayFormatDto> dtoList;

        if (id != null && id != "") {
            dtoList = dayFormatService.dayFormatSelect3(userid, id);
        }
        else {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .error("error")
                    .build());
        }

        return ResponseEntity.ok().body(ResponseDto.<DayFormatDto>builder()
                .error("success")
                .data(dtoList)
                .build());
    }
    @PostMapping("/dayFormatSave")
<<<<<<< HEAD
    public ResponseEntity<String> dayFormatSave(@RequestBody List<DayFormatDto> dtoList, int travelId){
        System.out.println("dtoList = " + dtoList);
        if(travelId == 0){
            System.out.println("Error");
        }else{
            dayFormatService.dayFormatSave(dtoList,travelId);
        }
        return ResponseEntity.ok().body("save");
=======
    public ResponseEntity<ResponseDto> dayFormatSave(@RequestBody List<DayFormatDto> dtoList,
             @RequestParam(value = "id")String id,@AuthenticationPrincipal String userid){

        if (id != null && id != "") {
            dayFormatService.dayFormatSave(dtoList,id,userid);
        }
        else {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .error("error")
                    .build());
        }

        return ResponseEntity.ok().body(ResponseDto.<DayFormatDto>builder()
                .error("success")
                .build());
>>>>>>> 4ef98d9de7d6a84da431af6ffd86d2f0f90e9a3c
    }

    @PostMapping("/dayFormatDelete")
    public ResponseEntity<ResponseDto> dayFormatDelete(@RequestBody List<DayFormatDto> dayIdList){

        if (dayIdList != null) {
            dayFormatService.dayFormatDelete(dayIdList);
        }
        else {
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .error("error")
                    .build());
        }

        return ResponseEntity.ok().body(ResponseDto.<DayFormatDto>builder()
                .error("success")
                .build());
    }

}
