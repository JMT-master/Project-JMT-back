package com.jmt.dto;

import com.jmt.entity.KnowledgeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeSendDto {
    private Long num;
    private String userid;
    private String category;
    private String title;
    private String content;
    private int view;
    private String fileSendKey;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public static com.jmt.dto.KnowledgeSendDto toDto(KnowledgeEntity knowledgeEntity) {
        return com.jmt.dto.KnowledgeSendDto.builder()
                .num(knowledgeEntity.getNum())
                .userid(knowledgeEntity.getUserid().getEmail())
                .category(knowledgeEntity.getCategory())
                .title(knowledgeEntity.getTitle())
                .content(knowledgeEntity.getContent())
                .view(knowledgeEntity.getView())
                .fileSendKey(knowledgeEntity.getFileKey())
                .regDate(knowledgeEntity.getRegDate())
                .modDate(knowledgeEntity.getModDate())
                .build();
    }

    public static KnowledgeEntity toEntity(KnowledgeSendDto knowledgeSendDtoDto) {
        return KnowledgeEntity.builder()
                .num(knowledgeSendDtoDto.getNum())
                .title(knowledgeSendDtoDto.getTitle())
                .content(knowledgeSendDtoDto.getContent())
                .category(knowledgeSendDtoDto.getCategory())
                .view(knowledgeSendDtoDto.getView())
                .fileKey(knowledgeSendDtoDto.getFileSendKey())
                .build();
    }
}
