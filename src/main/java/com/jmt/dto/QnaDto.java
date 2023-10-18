package com.jmt.dto;

import com.jmt.entity.QnaEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@ToString
public class QnaDto {

    private String qnaId;

    @NotBlank(message = "제목은 필수입니다.") // 빈 문자열 또는 null일 경우 에러 메시지를 설정한 어노테이션
    private String qnaTitle; // Q&A 제목을 나타내는 필드

    @NotBlank(message = "내용은 필수입니다.") // 빈 문자열 또는 null일 경우 에러 메시지를 설정한 어노테이션
    private String qnaContent; // Q&A 내용을 나타내는 필드

    private int qnaView;

    private String qnaCategory;

    private String qnaFileKey;

    //entity를 dto로
    public QnaDto(final QnaEntity qnaEntity) {
        this.qnaId = qnaEntity.getId();
        this.qnaTitle = qnaEntity.getQnaTitle();
        this.qnaContent = qnaEntity.getQnaContent();
        this.qnaCategory = qnaEntity.getQnaCategory();
        this.qnaView = qnaEntity.getQnaView();
        this.qnaFileKey = qnaEntity.getQnaFileKey();
    }

    //dto를 entity로
    public static QnaEntity toEntity(final QnaDto qnaDto){
        return QnaEntity.builder()
                .id(qnaDto.getQnaId())
                .qnaTitle(qnaDto.getQnaTitle())
                .qnaContent(qnaDto.getQnaContent())
                .qnaCategory(qnaDto.getQnaCategory())
                .qnaView(qnaDto.getQnaView())
                .qnaFileKey(qnaDto.getQnaFileKey())
                .build();
    }

}

