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
public class Alarm {
    @Id
    @Column(nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String alarmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_userid", referencedColumnName = "userid", nullable = false)
    private Member member;

    @Column(length = 1200, nullable = false)
    private String alarmContent;

    private String alarmUrl;
    @Column(length = 1, nullable = false)
    private String alarmYn;


}
