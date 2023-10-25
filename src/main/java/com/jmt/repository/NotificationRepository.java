package com.jmt.repository;

import com.jmt.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    public List<Notification> findNotificationsByMember_Userid(String userid);

    public Notification findByNotificationId(String alarmId);


}
