package com.jmt.controller;

import com.jmt.dto.QnaDto;
import com.jmt.dto.ResponseDto;
import com.jmt.entity.QnaEntity;
import com.jmt.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/qna")
public class QnaController {

    @Autowired
    private QnaService qnaService;

    @PostMapping("/write")
    public ResponseEntity<?> createQna(@RequestBody QnaDto qnaDto,
                                        String userid){

        try {
            QnaEntity qnaEntity = QnaDto.toEntity(qnaDto);
            qnaEntity.setId(null);
            qnaEntity.setQnaUserId(userid);

            List<QnaEntity> qnaEntities = qnaService.create(qnaEntity);
            List<QnaDto> qnaDtos = qnaEntities.stream().map(QnaDto::new)
                    .collect(Collectors.toList());
            ResponseDto<QnaDto> response = ResponseDto.<QnaDto>builder()
                    .data(qnaDtos)
                    .build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<QnaDto> responseDto = ResponseDto.<QnaDto>builder()
                    .error(error)
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }

    }

//    @GetMapping
//

}
