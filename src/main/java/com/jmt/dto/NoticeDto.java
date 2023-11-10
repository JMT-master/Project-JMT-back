package com.jmt.dto;

import com.jmt.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
    private Long idx;
    private String category;
    private String content;
    private String title;
    private String socialYn;
    private int view;
    private String        fileKey;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private List<String> files;

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
                    .view(entity.getNoticeView())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
