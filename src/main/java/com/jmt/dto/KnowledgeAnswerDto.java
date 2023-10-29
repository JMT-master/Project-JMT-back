package com.jmt.dto;

import com.jmt.entity.KnowledgeAnswerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeAnswerDto {
    private Long knNum;            // 글번호
    private String answerWriter;   // 작성자
    private String content;        // 내용
    private int answerLike;        // 좋아요 수
    private LocalDateTime regDate; // 생성날짜
    private LocalDateTime modDate; // 수정날짜

    public static KnowledgeAnswerDto toDto(KnowledgeAnswerEntity answerEntity) {
        return KnowledgeAnswerDto.builder()
                .knNum(answerEntity.getKnNum())
                .answerWriter(answerEntity.getAnswerWriter())
                .content(answerEntity.getContent())
                .answerLike(answerEntity.getAnswerLike())
                .modDate(answerEntity.getModDate())
                .regDate(answerEntity.getRegDate())
                .build();
    }
    
    public static KnowledgeAnswerEntity toEntity(KnowledgeAnswerDto answerDto){
        return KnowledgeAnswerEntity.builder()
                .knNum(answerDto.getKnNum())
                .answerWriter(answerDto.getAnswerWriter())
                .content(answerDto.getContent())
                .answerLike(answerDto.getAnswerLike())
                .build();
    }
}
