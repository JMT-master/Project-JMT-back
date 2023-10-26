package com.jmt.dto;

import com.jmt.entity.Notice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {
    private String category;
    private String content;
    private String title;

    public static Notice toEntity(final NoticeDto dto) {
        try {
            return Notice.builder()
                    .noticeTitle(dto.getTitle())
                    .noticeCategory(dto.getCategory())
                    .noticeContent(dto.getContent())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
