package com.jmt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="travelSchedule")
public class TravelScheduleEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="travel_id")
    private String travelId;

    @Column(name="travel_userid")
    private String travelUserid;

    @Column(name="travel_title")
    private String travelTitle;

    @Column(name="travel_yn")
    private String travelYn;

    @Column(name="travel_pNum")
    private int travelPnum;

    @Column(name="travel_startDate")
    private LocalDateTime travelStartDate;

    @Column(name="travel_endDate")
    private LocalDateTime travelEndDate;

}
