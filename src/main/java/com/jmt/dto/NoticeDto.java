package com.jmt.dto;

import com.jmt.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeDto {

    private String category;
    private String content;
    private String title;
    private String fileKey;
    private Long noticeNum;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public NoticeDto(final Notice notice){
        this.title = notice.getNoticeTitle();
        this.content = notice.getNoticeContent();
        this.category = notice.getNoticeCategory();
        this.noticeNum = notice.getNoticeNum();
        this.fileKey = notice.getNoticeFileKey();
        this.regDate = notice.getRegDate();
        this.modDate = notice.getModDate();
    }

    public static Notice toEntity(final NoticeDto dto) {
        try {
            return Notice.builder()
                    .noticeTitle(dto.getTitle())
                    .noticeCategory(dto.getCategory())
                    .noticeContent(dto.getContent())
                    .noticeNum(dto.getNoticeNum())
                    .noticeFileKey(dto.getFileKey())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
