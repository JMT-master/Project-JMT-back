package com.jmt.entity;

import javax.persistence.*;

@Table(name="day_format")
public class DayFormatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="day_id")
    private String dayId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private TravelScheduleEntity dayTravelId;

    @Column(name="day_select")
    private int daySelect;   //여행일정에서 몇번째 날인지ex)첫번째날 두번째날

    @Column(name="day_Index")
    private int dayIndex;    //여행일정에서 각 시간별 영역의 인덱스 값

    @Column(name="day_Image")   //이미지 경로
    private String dayImage;


}
