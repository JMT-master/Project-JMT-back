package com.jmt.dto;

import com.jmt.entity.Qna;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaDto {

    @NotBlank(message = "제목은 필수입니다.") // 빈 문자열 또는 null일 경우 에러 메시지를 설정한 어노테이션
    private String qnaTitle; // Q&A 제목을 나타내는 필드

    @NotBlank(message = "내용은 필수입니다.") // 빈 문자열 또는 null일 경우 에러 메시지를 설정한 어노테이션
    private String qnaContent; // Q&A 내용을 나타내는 필드

    private Long qnaNum;

    private int qnaView;

    private String qnaCategory;

    private String qnaFileKey;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
    //entity를 dto로
    public QnaDto(final Qna qna) {
        this.qnaTitle = qna.getQnaTitle();
        this.qnaContent = qna.getQnaContent();
        this.qnaCategory = qna.getQnaCategory();
        this.qnaView = qna.getQnaView();
        this.qnaFileKey = qna.getQnaFileKey();
        this.qnaNum = qna.getQnaNum();
        this.regDate = qna.getRegDate();
        this.modDate = qna.getModDate();
    }

    //dto를 entity로
    public static Qna toEntity(final QnaDto qnaDto){
        return Qna.builder()
                .qnaTitle(qnaDto.getQnaTitle())
                .qnaContent(qnaDto.getQnaContent())
                .qnaCategory(qnaDto.getQnaCategory())
                .qnaView(qnaDto.getQnaView())
                .qnaFileKey(qnaDto.getQnaFileKey())
                .qnaNum(qnaDto.getQnaNum())
                .build();
    }

}

