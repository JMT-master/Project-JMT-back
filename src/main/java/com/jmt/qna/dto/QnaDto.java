package com.jmt.qna.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@ToString
public class QnaDto {

    @NotBlank(message = "제목은 필수입니다.") // 빈 문자열 또는 null일 경우 에러 메시지를 설정한 어노테이션
    private String qnaTitle; // Q&A 제목을 나타내는 필드

    @NotBlank(message = "내용은 필수입니다.") // 빈 문자열 또는 null일 경우 에러 메시지를 설정한 어노테이션
    private String qnaContent; // Q&A 내용을 나타내는 필드
}
