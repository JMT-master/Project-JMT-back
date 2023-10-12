package com.jmt.qna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Hello {

    @GetMapping
    public String hello(){
        System.out.println("///");
        return "layout/hello";
    }
}
