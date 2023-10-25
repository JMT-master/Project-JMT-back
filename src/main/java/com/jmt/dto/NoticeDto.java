package com.jmt.dto;

import com.jmt.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NoticeDto {
    private Long idx;
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

    public static NoticeDto toDto(final Notice entity) {
        try {
            return NoticeDto.builder()
                    .title(entity.getNoticeTitle())
                    .category(entity.getNoticeCategory())
                    .content(entity.getNoticeContent())
                    .idx(entity.getNoticeIdx())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
