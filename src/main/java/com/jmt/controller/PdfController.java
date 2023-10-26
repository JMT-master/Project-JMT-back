package com.jmt.controller;

import com.jmt.entity.Travel;
import com.jmt.entity.TravelPlan;
import com.jmt.service.PdfService;
import com.jmt.service.TravelPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/travel")
@RequiredArgsConstructor
public class PdfController {

//    private final PdfService pdfService;

    private final TravelPdfService travelPdfService;

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody List<Travel> travelList) throws Exception {

//        TravelPlan travelPlan = new TravelPlan();

        System.out.println("컨트롤러에서 들어온 travelPlan = " + travelList);
        
        byte[] pdfBytes = travelPdfService.generatePdf(travelList);

        HttpHeaders headers = new HttpHeaders();
        //pdf로 만들기
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=travel_schedule.pdf");
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
//        Travel travel = new Travel();
//        //임의에 travelplan을 만들어서 travel 객체에 담는 코드
//        travel.setTravelPlans(TravelPlan.createTravelPlans(3));
//        ByteArrayOutputStream pdfBytes = pdfService.generatePdf(travel);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", "travel-plan.pdf");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(pdfBytes.size())
//                .body(pdfBytes.toByteArray());
//    }
//}
