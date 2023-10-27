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
@Entity
@Table(name="travelSchedule")
public class TravelScheduleEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer travelId;

    private String travelUserId;

    @Column(name="travel_title")
    private String travelTitle;

    private String travelYn;

    @Column(name="travel_pnum")
    private int travelPnum;

    @Column(name="travel_startDate")
    private LocalDateTime travelStartDate;

    @Column(name="travel_endDate")
    private LocalDateTime travelEndDate;

    @Column(name="travle_startTime")
    private LocalDateTime travelStartTime;

    @Column(name="travle_endTime")
    private LocalDateTime travleEndTime;

}
