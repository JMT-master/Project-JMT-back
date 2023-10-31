package com.jmt.controller;

import com.jmt.dto.DayFormatDto;
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

    @PostMapping("/dayFormatSelect1")
    public ResponseEntity<String> dayFormatSelect1(@AuthenticationPrincipal String userid,@RequestBody int travelId){

        dayFormatService.dayFormatSelect1(userid,travelId);

        return ResponseEntity.ok().body("save");
    }

    @PostMapping("/dayFormatSelect2")
    public ResponseEntity<String> dayFormatSelect2(@AuthenticationPrincipal String userid,@RequestBody int travelId){

        dayFormatService.dayFormatSelect2(userid,travelId);

        return ResponseEntity.ok().body("save");
    }

    @PostMapping("/dayFormatSelect3")
    public ResponseEntity<String> dayFormatSelect3(@AuthenticationPrincipal String userid,@RequestBody int travelId){

        dayFormatService.dayFormatSelect3(userid,travelId);

        return ResponseEntity.ok().body("save");
    }
    @PostMapping("/dayFormatSave")
    public ResponseEntity<String> dayFormatSave(@RequestBody Map<String,Object> dtoList, @RequestParam(value = "id")String id){

        System.out.println("dtoList = " + dtoList);
        System.out.println("id = " + id);
        dayFormatService.dayFormatSave(dtoList,id);

        return ResponseEntity.ok().body("save");
    }

    @PostMapping("/dayFormatDelete")
    public ResponseEntity<String> dayFormatDelete(@RequestParam(value = "id")String id){

        dayFormatService.dayFormatDelete(id);

        return ResponseEntity.ok().body("delete");
    }

}
