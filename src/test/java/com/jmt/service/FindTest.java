package com.jmt.service;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class FindTest {

    @Autowired
    private ChatService chatService;

    @Test
    public void test(){
        log.info("전체 값 읽어올꺼임"+chatService.readRoom());
    }


}
