package com.jmt.dto;

import com.jmt.entity.KnowledgeEntity;
import com.jmt.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class NoticeSendDto {
    // KnowledgeDetail 관련
    private Long idx;
    private String userid;
    private String category;
    private String title;
    private String content;
    private int view;
    private String serverPath;
    private String originalName;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public static NoticeSendDto toDto(Notice notice) {
        return NoticeSendDto.builder()
                .idx(notice.getNoticeIdx())
                .userid(notice.getMember().getEmail())
                .category(notice.getNoticeCategory())
                .title(notice.getNoticeTitle())
                .content(notice.getNoticeContent())
                .view(notice.getNoticeView())
                .regDate(notice.getRegDate())
                .modDate(notice.getModDate())
                .build();
    }

    public static KnowledgeEntity toEntity(NoticeSendDto knowledgeSendDtoDto) {
        return KnowledgeEntity.builder()
                .num(knowledgeSendDtoDto.getIdx())
                .title(knowledgeSendDtoDto.getTitle())
                .content(knowledgeSendDtoDto.getContent())
                .category(knowledgeSendDtoDto.getCategory())
                .view(knowledgeSendDtoDto.getView())
                .build();
    }
}
