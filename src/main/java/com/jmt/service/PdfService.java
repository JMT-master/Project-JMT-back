package com.jmt.service;

import com.jmt.entity.Travel;
import com.jmt.entity.TravelPlan;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public ByteArrayOutputStream generatePdf(Travel travel) throws IOException{
        //stream 생성
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //pdf 객체 만들기
        PDDocument document = new PDDocument();
        //한글 폰트 파일의 절대 경로
        String fontFilePath = "D:\\final-project\\final\\src\\main\\resources\\static\\NanumGothic-Regular.ttf";
        //폰트 파일을 스트림으로 읽을 꼬야
        try(InputStream fontStream = Files.newInputStream(Paths.get(fontFilePath))) {
            for (TravelPlan travelPlan : travel.getTravelPlans()) {
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                // BufferedImage image = loadImage(event.getImageUrl()); // Load image using an appropriate method
                // PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);
                // contentStream.drawImage(pdImage, x, y, width, height);
                //pdf 만들기 시작
                contentStream.beginText();
                //한글 폰트 설정!!
                contentStream.setFont(PDType0Font.load(document, fontStream), 12);
                //x, y 길이 설정!
                contentStream.newLineAtOffset(100, 700);
                //내용 하나씩 적기
                contentStream.showText("시간 : "+travelPlan.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd :HH:mm")));
                contentStream.newLine();
                contentStream.showText("장소 : "+travelPlan.getPlace());
                contentStream.newLine();
                contentStream.showText("장소 이름 : "+travelPlan.getPlaceName());
                contentStream.endText();
                contentStream.close();
            }
        }
        document.save(byteArrayOutputStream);
        document.close();
        return byteArrayOutputStream;
    }

}
