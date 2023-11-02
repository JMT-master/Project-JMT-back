package com.jmt.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table
public class Notification extends BaseTimeEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_userid", referencedColumnName = "userid", nullable = false)
    private Member member;

    @Column(name = "notify_num")
    private Long notifyIdx;
    @Column(length = 1200, nullable = false)
    private String notificationContent;

    private String notificationUrl;
    @Column(length = 1, nullable = false)
    private String notificationYn;


}
