package com.jmt.controller;

import com.jmt.entity.Travel;
import com.jmt.entity.TravelPlan;
import com.jmt.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/travel")
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;

    @GetMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf() throws IOException {

        Travel travel = new Travel();
        //임의에 travelplan을 만들어서 travel 객체에 담는 코드
        travel.setTravelPlans(TravelPlan.createTravelPlans(3));
        ByteArrayOutputStream pdfBytes = pdfService.generatePdf(travel);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "travel-plan.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfBytes.size())
                .body(pdfBytes.toByteArray());
    }
}
