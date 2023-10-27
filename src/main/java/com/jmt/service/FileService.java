package com.jmt.service;

import com.jmt.constant.Board;
import com.jmt.dto.KnowledgeDto;
import com.jmt.entity.KnowledgeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {
    @Value("${itemImgLocation}")
    private String itemImageLocation;

    // 파일, user, KN/QNA/NOTICE, 글번호
    public List<String> fileUpload(List<MultipartFile> multipartFiles, String userid, Board fileIdName, int count) {
        String divideBoard = "";
        String fileKey = "";
        List<String> fileKeys = new ArrayList<>();

        File folder = new File(itemImageLocation);

        if(!folder.exists()) folder.mkdirs();

        if(fileIdName == Board.KN) {
            divideBoard = "kn_" + (count + 1) + "_";
        }

        for(int i=0; i < multipartFiles.size(); i++) {
            fileKey = divideBoard + (i+1) + "_" + multipartFiles.get(i).getOriginalFilename();
            String fileUploadFullUrl = itemImageLocation + "/" + fileKey;

            System.out.println("fileKey = " + fileKey);
            System.out.println("multipartFiles = " + multipartFiles.get(0).getSize());
            System.out.println("fileUploadFullUrl = " + fileUploadFullUrl);
            System.out.println("fileIdName = " + fileIdName);
            System.out.println("userid = " + userid);
            try {
                FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
                fos.write(multipartFiles.get(i).getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("파일 저장 오류");
            }

            fileKeys.add(fileKey);
        }

        return fileKeys;
    }
}
