package com.jmt.dto;

import com.jmt.entity.Notice;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class NoticeDto {
    private String noticeId;
    private String noticeCategory;
    private String noticeContent;
    private String noticeTitle;

    private static ModelMapper modelMapper = new ModelMapper();

    public Notice dtoToNotice(){
        return modelMapper.map(this, Notice.class);
    }
}
