package com.jmt.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    void sendMail() {
        emailService.sendMail("nc199@hanmail.net");
    }

}