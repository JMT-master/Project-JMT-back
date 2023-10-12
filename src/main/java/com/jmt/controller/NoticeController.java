package com.jmt.controller;

import com.jmt.dto.NoticeDto;
import com.jmt.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class NoticeController {

    NoticeService noticeService;

    @GetMapping("/write")
    public void writeNotice(@RequestBody NoticeDto dto){
        noticeService.createNotice(dto);
    }
}
