package com.jmt.common;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.gimpy.DropShadowGimpyRenderer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.NumbersAnswerProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CaptchaUtil {
    public String getCaptcaImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // 폰트 및 컬러 설정
            List<Font> fontList = new ArrayList<>();

            fontList.add(new Font("", Font.HANGING_BASELINE,50));
            fontList.add(new Font("Courier",Font.CENTER_BASELINE,50));
            fontList.add(new Font("",Font.PLAIN,50));
            List<Color> colorList = new ArrayList<>();
            colorList.add(Color.red);
            colorList.add(Color.blue);
            colorList.add(Color.green);
            colorList.add(Color.pink);
            colorList.add(Color.cyan);
            colorList.add(Color.black);

            // 크기나 숫자
            Captcha captcha = new Captcha.Builder(200, 60)
//                    .addText(new NumbersAnswerProducer(6), new DefaultWordRenderer(colorList, fontList))
                    .addText(new DefaultWordRenderer(colorList, fontList))
                    .addBackground(new GradiatedBackgroundProducer(Color.white, Color.BLACK)) // Background Color
//                    .gimp(new DropShadowGimpyRenderer()) // 이미지를 꼬으려고 만듬
                    .addNoise().addNoise() // 방해선
                    .addBorder()
                    .build();

//            response.setHeader("Cache-Control", "no-store");
//            response.setHeader("Pragma", "no-cache");        // 브라우저에 캐쉬를 지우기 위한 헤더값 설정
//            response.setDateHeader("Expires",0);              // 만료기간
//            response.setContentType("image/jpeg");                 // 리턴을 image로
//            request.getSession().setAttribute(Captcha.NAME, captcha);

            // 세션에 캡차 등록 -> 사용자가 입력한 보안 문자와 비교하기 위해
            CaptchaServletUtil.writeImage(response, captcha.getImage());
            String captchaStr =captcha.getAnswer();
            System.out.println("세션 : "+ request.getSession());

            return captchaStr;

        } catch (Exception e) {
            e.getMessage();
        }

        return "";
    }
}
