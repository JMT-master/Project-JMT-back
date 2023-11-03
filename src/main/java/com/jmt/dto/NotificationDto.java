package com.jmt.dto;

import com.jmt.entity.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class NotificationDto {
    String id;
    String title;
    String userid;
    String content;
    String url;
    String yn;

    public static Notification toEntity(final NotificationDto dto) {
        try {
            return Notification.builder()
                    .notificationContent(dto.getTitle() + "글에 " +dto.getContent() + " 답글이 작성되었습니다.")
                    .notificationUrl(dto.getUrl())
                    .notificationYn(dto.getYn())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static NotificationDto toDto(final Notification entity) {
        try {
            return NotificationDto.builder()
                    .id(entity.getNotificationId())
                    .content(entity.getNotificationContent())
                    .url(entity.getNotificationUrl())
                    .yn(entity.getNotificationYn())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
