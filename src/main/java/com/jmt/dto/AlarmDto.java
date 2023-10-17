package com.jmt.dto;

import com.jmt.entity.Alarm;
import com.jmt.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmDto {

    String userid;
    String content;
    String url;
    String yn;

    public static Alarm toEntity(final AlarmDto dto, final Member member) {
        try {
            return Alarm.builder()
                    .member(member)
                    .alarmContent(dto.getContent())
                    .alarmUrl(dto.getUrl())
                    .alarmYn(dto.getYn())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
