package com.jmt.dto;

import com.jmt.entity.Qna;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaDetailDto {

    //qna 파일 관련 dto

    private Long qnaNum;

    private String qnaTitle;

    private String qnaContent;

    private int qnaView;

    private String qnaCategory;

    private String serverPath;

    private String originalName;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
    //entity를 dto로
    public QnaDetailDto(final Qna qna) {
        this.qnaTitle = qna.getQnaTitle();
        this.qnaContent = qna.getQnaContent();
        this.qnaCategory = qna.getQnaCategory();
        this.qnaView = qna.getQnaView();
        this.qnaNum = qna.getQnaNum();
        this.regDate = qna.getRegDate();
        this.modDate = qna.getModDate();
    }

    //dto를 entity로
    public static Qna toEntity(final QnaDetailDto qnaDetailDto){
        return Qna.builder()
                .qnaTitle(qnaDetailDto.getQnaTitle())
                .qnaContent(qnaDetailDto.getQnaContent())
                .qnaCategory(qnaDetailDto.getQnaCategory())
                .qnaView(qnaDetailDto.getQnaView())
                .qnaNum(qnaDetailDto.getQnaNum())
                .build();
    }
}
