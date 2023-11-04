package com.jmt.service;

import org.apache.pdfbox.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

@Service
@Transactional
public class FontService {

    private final ResourceLoader resourceLoader;

    public FontService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public byte[] loadFontFile() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/Nanum_Gothic/NanumGothic-Regular.ttf");
        InputStream inputStream = resource.getInputStream();
        return IOUtils.toByteArray(inputStream); // IOUtils는 Apache Commons IO 라이브러리에서 제공하는 유틸리티 클래스입니다.
    }
}
