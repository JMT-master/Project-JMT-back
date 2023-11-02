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

    public void createEmailCode() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789"; // 숫자와 문자열을 섞을 문자열

        for (int i = 0; i < 8; i++) {
            // 랜덤한 인덱스를 생성해서 characters 문자열에서 문자를 선택하거나 숫자를 선택
            char randomChar = characters.charAt(rnd.nextInt(characters.length()));
            key.append(randomChar);
        }

        System.out.println("랜덤한 문자열: " + key.toString());
    }

    public void sendNewPwdMail(String email){
        createEmailCode();
        sendEmailID=email;
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        StringBuffer sendMsg = pwdMessage();

        try {
            helper.setFrom("JMT@JMT.com");
            helper.setTo(email);
            helper.setSubject("JMT에서 새로운 비밀번호를 보내드립니다");
            helper.setText(sendMsg.toString(), true);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("이메일 형식이 맞지 않습니다.");
        }

        javaMailSender.send(message);
    }

    public StringBuffer pwdMessage() {
        StringBuffer sendEmail = new StringBuffer();
        String emailAddress = "http://localhost:3000/checkNewPwd" + sendEmailID;

        sendEmail.append(
                        "<div>"+
                        "<h3>JMT 새 비밀번호 입니다.</h3>" +
                        "<p>아래 링크에서 비밀번호 변경을 진행하세요.</p>" +
                        "<a href = \"" + emailAddress + "\">비밀번호 변경</a>" +
                        "</div>"
        );

        return sendEmail;
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
