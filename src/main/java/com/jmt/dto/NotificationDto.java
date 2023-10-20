package com.jmt.dto;

import com.jmt.entity.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationDto {
    String id;
    String content;
    String url;
    String yn;

    public static Notification toEntity(final NotificationDto dto) {
        try {
            return Notification.builder()
                    .notificationId(dto.getId())
                    .notificationContent(dto.getContent())
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
