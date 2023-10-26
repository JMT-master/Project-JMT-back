package com.jmt.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.jmt.entity.Travel;
import com.jmt.entity.TravelPlan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@Transactional
public class TravelPdfService {

    public byte[] generatePdf(List<Travel> travelList) throws Exception {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(pdfWriter);
        Document document = new Document(pdf);

        System.out.println("서비스에서 확인하는 travelList = " + travelList);
//        System.out.println("서비스에서 확인하는 travelList.getTravels = " + travelList.getTravels());

        //여행 일정 데이터를 PDF에 추가하는 방법 다시
        for (Travel travel : travelList){
            document.add(new Paragraph("날짜 : " + travel.getStartTime()));
            document.add(new Paragraph("주소 : " + travel.getPlace()));
            document.add(new Paragraph("장소 이름 : " + travel.getPlaceName()));
            document.add(new Paragraph("------------------"));
        }
        document.close();
        return outputStream.toByteArray();
    }

}
