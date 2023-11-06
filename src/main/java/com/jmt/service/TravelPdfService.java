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
import com.jmt.dto.DayFormatDto;
import com.jmt.dto.TravelScheduleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TravelPdfService {

    private final TravelScheduleService travelScheduleService;
    private final DayFormatService dayFormatService;
    private final FontService fontService;

    public byte[] generatePdf(String travelId,
                               String userId) throws Exception {

//        System.out.println("userId = " + userId);
//        System.out.println("travelId = " + travelId);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(pdfWriter);
        Document document = new Document(pdf);

        // Google 폰트 로딩
//        Path regularFontFilePath = Path.of("D:\\final-project\\final\\src\\main\\resources\\static\\Nanum_Gothic\\NanumGothic-Regular.ttf"); // 다운로드한 폰트 파일의 경로를 지정하세요
        byte[] fontBytes = fontService.loadFontFile();

        // 문서에 폰트 설정
        FontProgram fontProgram = FontProgramFactory.createFont(fontBytes);
        PdfFont customFont = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);


        //이미지 만드는 부분
//        ImageData imageData = ImageDataFactory.create("D:\\final-project\\final\\src\\main\\resources\\static\\image\\035e1737735049018a2ed2964dda596c_750S.jpg");
//        Image image = new Image(imageData).scaleAbsolute(100,200);

        //여행 일정 데이터를 PDF에 추가하는 방법
        //travelSchdule 부터 일단 가져오기
        TravelScheduleDto travelScheduleDto = travelScheduleService.selectByTravelId(travelId);
        System.out.println("travelScheduleDto = " + travelScheduleDto);

        document.add(new Paragraph("여행 일정 제목 : "+travelScheduleDto.getTravelTitle())
                .setFont(customFont).setFontSize(25));

        document.add(new Paragraph("여행 시작일 : "+travelScheduleDto.getTravelStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                +" ~~ 여행 종료일 : "+travelScheduleDto.getTravelEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setFont(customFont));

        List<DayFormatDto> dayFormatDtoList1 = dayFormatService.dayFormatSelect1(userId, travelId);

        document.add(new Paragraph("1일차 일정")).setFont(customFont);
        //시간 표시용 String 값 하나
        String timeIndex;
        for (DayFormatDto dayFormatDto : dayFormatDtoList1) {
                ImageData imageData = ImageDataFactory.create(dayFormatDto.getDayImage());
                Image image = new Image(imageData).scaleAbsolute(100,100);
                if (dayFormatDto.getDayIndex() / 12 > 0){
                    timeIndex = " 오후 " + dayFormatDto.getDayIndex() % 12 + "시";
                }else{
                    timeIndex = " 오전 " + dayFormatDto.getDayIndex() % 12 + "시";
                }
                document.add(image);
                document.add(new Paragraph("장소 명 : "+dayFormatDto.getDayTitle())).setFont(customFont);
                document.add(new Paragraph("위치 : "+dayFormatDto.getDayRegion1()+" -- "+dayFormatDto.getDayRegion2()))
                        .setFont(customFont);
            document.add(new Paragraph("시간 : " + timeIndex).setFont(customFont));
            document.add(new Paragraph("---------------------------------------------"));
            }

        List<DayFormatDto> dayFormatDtoList2 = dayFormatService.dayFormatSelect2(userId, travelId);

        document.add(new Paragraph("2일차 일정")).setFont(customFont);
        for (DayFormatDto dayFormatDto : dayFormatDtoList2) {
            ImageData imageData = ImageDataFactory.create(dayFormatDto.getDayImage());
            Image image = new Image(imageData).scaleAbsolute(150,150);
            if (dayFormatDto.getDayIndex() / 12 > 0){
                timeIndex = " 오후 " + dayFormatDto.getDayIndex() % 12 + "시";
            }else{
                timeIndex = " 오전 " + dayFormatDto.getDayIndex() % 12 + "시";
            }
            document.add(image);
            document.add(new Paragraph("장소 명 : "+dayFormatDto.getDayTitle())).setFont(customFont);
            document.add(new Paragraph("위치 : "+dayFormatDto.getDayRegion1()+" -- "+dayFormatDto.getDayRegion2()))
                    .setFont(customFont);
            document.add(new Paragraph("시간 : " + timeIndex).setFont(customFont));
            document.add(new Paragraph("---------------------------------------------"));
        }
        document.close();
        return outputStream.toByteArray();
    }

}
