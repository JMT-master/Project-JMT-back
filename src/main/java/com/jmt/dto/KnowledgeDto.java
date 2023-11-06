package com.jmt.dto;

import com.jmt.entity.KnowledgeEntity;
import com.jmt.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDto {
    private Long          num;
    private String        userid;
    private String        category;
    private String        title;
    private String        content;
    private int           view;
    private String        fileKey;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private String        socialYn;

    public static KnowledgeDto toDto(KnowledgeEntity knowledgeEntity) {
        return KnowledgeDto.builder()
                .num(knowledgeEntity.getNum())
                .userid(knowledgeEntity.getUserid().getEmail())
                .category(knowledgeEntity.getCategory())
                .title(knowledgeEntity.getTitle())
                .content(knowledgeEntity.getContent())
                .view(knowledgeEntity.getView())
                .fileKey(knowledgeEntity.getFileKey())
                .regDate(knowledgeEntity.getRegDate())
                .modDate(knowledgeEntity.getModDate())
                .build() ;
    }

    public static KnowledgeEntity toEntity(KnowledgeDto knowledgeDto) {
        return KnowledgeEntity.builder()
                .num(knowledgeDto.getNum())
                .title(knowledgeDto.getTitle())
                .content(knowledgeDto.getContent())
                .category(knowledgeDto.getCategory())
                .view(knowledgeDto.getView())
                .fileKey(knowledgeDto.getFileKey())
                .build();
    }
}
