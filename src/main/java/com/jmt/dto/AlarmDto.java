package com.jmt.dto;

import com.jmt.entity.Alarm;
import com.jmt.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AlarmDto {

    String content;
    String url;
    String yn;

    public static Alarm toEntity(final AlarmDto dto) {
        try {
            return Alarm.builder()
                    .alarmContent(dto.getContent())
                    .alarmUrl(dto.getUrl())
                    .alarmYn(dto.getYn())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static AlarmDto toDto(final Alarm entity) {
        try {
            return AlarmDto.builder()
                    .content(entity.getAlarmContent())
                    .url(entity.getAlarmUrl())
                    .yn(entity.getAlarmYn())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
