package com.jmt.service;

import com.jmt.constant.Board;
import com.jmt.dto.KnowledgeDto;
import com.jmt.entity.KnowledgeEntity;
import com.jmt.entity.Member;
import com.jmt.entity.MemberFile;
import com.jmt.repository.MemberFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {
    @Value("${itemImgLocation}")
    private String itemImageLocation;

    private final MemberFileRepository memberFileRepository;

    // 파일, user, KN/QNA/NOTICE, 글번호
    public String fileUpload(List<MultipartFile> multipartFiles, String userid, Board fileIdName, int count) {
        String fileInfo = "";
        String divideBoard = "";
        String fileKey = "";
        List<String> fileKeys = new ArrayList<>();
        List<MemberFile> memberFiles = new ArrayList<>();

        File folder = new File(itemImageLocation);

        if(!folder.exists()) folder.mkdirs();

        if(fileIdName == Board.KN) {
            divideBoard = "KN_" + count  + "_";
            fileInfo = "KN_" + count;
        }else if (fileIdName == Board.QNA){
            divideBoard = "QNA_" + count  + "_";
            fileInfo = "QNA_" + count;
        }else {
            divideBoard = "NOTICE_" + count  + "_";
            fileInfo = "NOTICE_" + count;
        }

        for(int i=0; i < multipartFiles.size(); i++) {
            fileKey = divideBoard + (i+1) + "_" + multipartFiles.get(i).getOriginalFilename();
            String fileUploadFullUrl = itemImageLocation + "/" + fileKey;

            System.out.println("fileKey = " + fileKey);
            System.out.println("multipartFiles = " + multipartFiles.get(0).getSize());
            System.out.println("fileUploadFullUrl = " + fileUploadFullUrl);
            System.out.println("fileIdName = " + fileIdName);
            System.out.println("userid = " + userid);
            System.out.println("multipartFiles.get(i).getName() = " + multipartFiles.get(i).getName());
            System.out.println("multipartFiles.get(i).getOriginalFilename() = " + multipartFiles.get(i).getOriginalFilename());

            try {
                FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
                fos.write(multipartFiles.get(i).getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("파일 저장 오류");
            }

            fileKeys.add(fileKey);

            memberFiles.add(MemberFile.builder()
                    .fileId(fileKey)
                    .fileName(multipartFiles.get(i).getOriginalFilename())
                    .fileSize(multipartFiles.get(i).getSize())
                    .fileServerPath(fileUploadFullUrl)
                    .fileCategory(fileIdName.toString())
                    .fileUserId(userid)
                    .fileInfo(fileInfo)
                    .build());
        }

        memberFileRepository.saveAll(memberFiles);

        return fileInfo;
    }

    public String generateRandomString() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int STRING_LENGTH = 20;
        final SecureRandom secureRandom = new SecureRandom();

        StringBuilder sb = new StringBuilder(STRING_LENGTH);
        for (int i = 0; i < STRING_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
