package com.jmt.service;

import com.jmt.entity.Travel;
import com.jmt.entity.TravelPlan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PdfService {

    public ByteArrayOutputStream generatePdf(Travel travel) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        /*

        PDDocument document = new PDDocument();

        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream  = new PDPageContentStream(document, page);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(50, 700);
        contentStream.showText("h2");
        contentStream.endText();
        contentStream.close();
        */

//        PDDocument document = new PDDocument();

//        PDPage blankPage = new PDPage();
//        document.addPage(blankPage);
//        PDPageContentStream contentStream  = new PDPageContentStream(document, blankPage);
//        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
//        contentStream.beginText();
//        contentStream.newLineAtOffset(50, 700);
//        contentStream.showText("hello world");
//        contentStream.endText();
//        contentStream.close();
//        document.save("D:\\my_pdf.pdf");
//        System.out.println("들어옴?");
//
//        document.close();

        try {
            PDDocument document = new PDDocument();
            String fontFilePath = "D:\\final-project\\final\\src\\main\\resources\\static\\NanumGothic-Regular.ttf";
            try {
                InputStream fontStream = Files.newInputStream(Paths.get(fontFilePath));
                List<TravelPlan> travelPlans = travel.getTravelPlans();

                for (int i = 0; i < travelPlans.size(); i++) {
                    TravelPlan travelPlan = travelPlans.get(i);
                    PDPage page = new PDPage();
                    document.addPage(page);

                    // Build content as a string for debugging
                    StringBuilder contentBuilder = new StringBuilder();
                    contentBuilder.append("시간 : ").append(travelPlan.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd :HH:mm"))).append("\n");
                    contentBuilder.append("장소 : ").append(travelPlan.getPlace()).append("\n");
                    contentBuilder.append("장소 이름 : ").append(travelPlan.getPlaceName());

                    // Print content for debugging
                    System.out.println("Content for Page " + (i + 1) + ":\n" + contentBuilder.toString());

                    try {
                        PDPageContentStream contentStream = new PDPageContentStream(document, page);

//                        if (i == 0) {
//                        }
                        contentStream.beginText();
                        contentStream.setFont(PDType0Font.load(document, fontStream), 12);
                        contentStream.newLineAtOffset(100, 700);
                        contentStream.showText("시간 : " + travelPlan.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd :HH:mm")));
                        contentStream.newLine();
                        contentStream.showText("장소 : " + travelPlan.getPlace());
                        contentStream.newLine();
                        contentStream.showText("장소 이름 : " + travelPlan.getPlaceName());
                        contentStream.endText();
                        contentStream.close();

//                        if (i == travelPlans.size()) {
//                        }
                    } catch (Exception e) {
                        String error = e.getMessage();
                        System.out.println("for문 도중 error = " + error);
                    }
                }
                fontStream.close();
                document.save("D:\\my_pdf.pdf");
                document.close();
            } catch (Exception e) {
                String error = e.getMessage();
                System.out.println(" for문 바깥 error  = " + error);
            }
        } catch (Exception e) {
            String error = e.getMessage();
            System.out.println(" 마지막 error  = " + error);
        }
        return byteArrayOutputStream;
    }

}
