package com.jmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeUpdateDto {
    private Long num;
    private String title;
    private String category;
    private String content;
    private String socialYn;
    private List<String> files;
}
