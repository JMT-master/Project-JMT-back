package com.jmt.controller;

import com.jmt.dto.ResponseDto;
import com.jmt.dto.TravelScheduleDto;
import com.jmt.dto.WishDto;
import com.jmt.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wish")
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    //travel destination 여행지 의 약자가  Tdn임ㅋㅋ ㅅㄱ요
    @PostMapping("/wishTdnInsert")
    public ResponseEntity<ResponseDto> wishTdnInsert(@RequestBody WishDto wishDto, @AuthenticationPrincipal String userid){

        if(wishDto != null){
            wishService.wishTdnInsert(wishDto,userid);
        }
        else{
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .error("error")
                    .build());
        }

        return ResponseEntity.ok().body(ResponseDto.<WishDto>builder()
                .error("success")
                .build());
    }
    //travel plans 여행지 의 약자가  Tps임ㅋㅋ ㅅㄱ요
    @PostMapping("/wishTpsInsert")
    public ResponseEntity<ResponseDto> wishTpsInsert(@RequestBody WishDto wishDto, @AuthenticationPrincipal String userid){
        if(wishDto != null){
            wishService.wishTpsInsert(wishDto,userid);
        }
        else{
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .error("error")
                    .build());
        }

        return ResponseEntity.ok().body(ResponseDto.<WishDto>builder()
                .error("success")
                .build());
    }



    @GetMapping("/wishTdnSelect")
    public ResponseEntity<ResponseDto> wishTdnSelect(@AuthenticationPrincipal String userid){

        List<WishDto> dtoList;
        if(userid != null && userid != ""){
            dtoList = wishService.wishTdnSelect(userid);
        }
        else{
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .error("error")
                    .build());
        }
        return ResponseEntity.ok().body(ResponseDto.<WishDto>builder()
                .error("success")
                .data(dtoList)
                .build());
    }

    @GetMapping("/wishTpsSelect")
    public ResponseEntity<ResponseDto> wishTpsSelect(@AuthenticationPrincipal String userid){

        List<WishDto> dtoList;
        if(userid != null && userid != ""){
            dtoList = wishService.wishTpsSelect(userid);
        }
        else{
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .error("error")
                    .build());
        }
        return ResponseEntity.ok().body(ResponseDto.<WishDto>builder()
                .error("success")
                .data(dtoList)
                .build());
    }

}
