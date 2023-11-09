package com.jmt.controller;

import com.jmt.dto.TravelPdfDto;
import com.jmt.service.TravelPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/travel")
@RequiredArgsConstructor
public class PdfController {

//    private final PdfService pdfService;

    private final TravelPdfService travelPdfService;

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody TravelPdfDto travelPdfDto) throws Exception {
        byte[] pdfBytes = travelPdfService.generatePdf(travelPdfDto.getTravelId(), travelPdfDto.getUserId());

        HttpHeaders headers = new HttpHeaders();
        //pdf로 만들기
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+travelPdfDto.getTravelTitle()+".pdf");
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}

