package com.jmt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private String sendEmailID;


    // 인증키 생성
    public void createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for(int i=0; i < 6; i++){
            key.append(rnd.nextInt(10));
        }
    }

    public void sendMail(String send) {
        createKey();
        sendEmailID = send;
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        StringBuffer sendMsg = htmlMessage();

        try {
            helper.setFrom("JMT@JMT.com");
            helper.setTo(send);
            helper.setSubject("JMT 회원가입 인증입니다!");
            helper.setText(sendMsg.toString(), true);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("이메일 형식이 맞지 않습니다.");
        }

        javaMailSender.send(message);
    }

    public StringBuffer htmlMessage() {
        StringBuffer sendEmail = new StringBuffer();
        String emailAddress = "http://localhost:3000/joinUser/email/validateCheck/" + sendEmailID;

        sendEmail.append(
                "<div>"+
                        "<h3>JMT 인증 요청 입니다.</h3>" +
                        "<p>아래 링크를 누를시 회원가입 됩니다.</p>" +
                        "<a href = \"" + emailAddress + "\">회원인증</a>" +
                        "</div>"
        );

        return sendEmail;
    }
}
