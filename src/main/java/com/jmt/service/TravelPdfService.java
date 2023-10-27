package com.jmt.service;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontProvider;
import com.jmt.entity.Travel;
import com.jmt.entity.TravelPlan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class TravelPdfService {

    public byte[] generatePdf(List<Travel> travelList) throws Exception {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(pdfWriter);
        Document document = new Document(pdf);

        // Google 폰트 로딩
        Path fontFilePath = Path.of("D:\\final-project\\final\\src\\main\\resources\\static\\Nanum_Gothic\\NanumGothic-Regular.ttf"); // 다운로드한 폰트 파일의 경로를 지정하세요
        byte[] fontBytes = Files.readAllBytes(fontFilePath);

        // 문서에 폰트 설정
        FontProgram fontProgram = FontProgramFactory.createFont(fontBytes);
        PdfFont customFont = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

        //이미지 만드는 부분
        ImageData imageData = ImageDataFactory.create("D:\\final-project\\final\\src\\main\\resources\\static\\image\\035e1737735049018a2ed2964dda596c_750S.jpg");
        Image image = new Image(imageData).scaleAbsolute(100,200);
        System.out.println("서비스에서 확인하는 travelList = " + travelList);
//        System.out.println("서비스에서 확인하는 travelList.getTravels = " + travelList.getTravels());

        //여행 일정 데이터를 PDF에 추가하는 방법 다시
        for (Travel travel : travelList){
            //이미지를 넣어보기 테스트중
            document.add(image);
            document.add(new Paragraph("날짜 : " + travel.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd : HH:mm")))
                    .setFont(customFont));
            document.add(new Paragraph("주소 : " + travel.getPlace())
                    .setFont(customFont));
            document.add(new Paragraph("장소 이름 : " + travel.getPlaceName())
                    .setFont(customFont));
            document.add(new Paragraph("------------------"));
        }
        document.close();
        return outputStream.toByteArray();
    }

}
