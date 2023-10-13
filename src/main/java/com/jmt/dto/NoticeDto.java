package com.jmt.dto;

import com.jmt.entity.Notice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {
    private String noticeCategory;
    private String noticeContent;
    private String noticeTitle;

    public static Notice toEntity(final NoticeDto dto) {
        try {
            return Notice.builder()
                    .noticeTitle(dto.getNoticeTitle())
                    .noticeCategory(dto.getNoticeCategory())
                    .noticeContent(dto.getNoticeContent())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
