package com.jmt.dto;

import com.jmt.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NoticeDto {
    private Long idx;
    private String category;
    private String content;
    private String title;
    private int view;
    private String        fileKey;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public static Notice toEntity(final NoticeDto dto) {
        try {
            return Notice.builder()
                    .noticeIdx(dto.getIdx())
                    .noticeTitle(dto.getTitle())
                    .noticeCategory(dto.getCategory())
                    .noticeContent(dto.getContent())
                    .noticeFileKey(dto.getFileKey())
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
                    .regDate(entity.getRegDate())
                    .modDate(entity.getModDate())
                    .fileKey(entity.getNoticeFileKey())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
