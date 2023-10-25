package com.jmt.dto;

import com.jmt.entity.KnowledgeEntity;
import com.jmt.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDto {
    private Long   num;
    private String title;
    private String content;
    private String category;
    private int view;
    private List<String> fileKey;

    public static KnowledgeDto toDto(KnowledgeEntity knowledgeEntity) {
        return KnowledgeDto.builder()
                .num(knowledgeEntity.getNum())
                .title(knowledgeEntity.getTitle())
                .content(knowledgeEntity.getContent())
                .category(knowledgeEntity.getCategory())
                .view(knowledgeEntity.getView())
                .build() ;
    }

    public static KnowledgeEntity toEntity(KnowledgeDto knowledgeDto) {
        return KnowledgeEntity.builder()
                .num(knowledgeDto.getNum())
                .title(knowledgeDto.getTitle())
                .content(knowledgeDto.getContent())
                .category(knowledgeDto.getCategory())
                .view(knowledgeDto.getView())
                .build();
    }
}
