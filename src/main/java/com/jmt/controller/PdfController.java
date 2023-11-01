package com.jmt.controller;

import com.jmt.service.TravelPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/travel")
@RequiredArgsConstructor
public class PdfController {

//    private final PdfService pdfService;

    private final TravelPdfService travelPdfService;

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf(@AuthenticationPrincipal String userId, String travelId) throws Exception {

        byte[] pdfBytes = travelPdfService.generatePdf("4028b8818b88c88c018b88c93d7b0000", "agh@agh.com");

        HttpHeaders headers = new HttpHeaders();
        //pdf로 만들기
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=travel_schedule.pdf");
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}

