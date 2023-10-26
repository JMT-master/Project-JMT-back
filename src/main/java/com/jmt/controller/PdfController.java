package com.jmt.controller;

import com.jmt.entity.Travel;
import com.jmt.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/travel/generate-pdf")
    public ResponseEntity<byte[]> generatePdf() throws IOException {
        Travel travel = new Travel();
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
