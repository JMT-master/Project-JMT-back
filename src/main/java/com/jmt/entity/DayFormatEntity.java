package com.jmt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="day_format")
public class DayFormatEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="day_id")
    private String dayId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_travelid")
    private TravelScheduleEntity dayTravelId;

    @Column(name="day_count")
    private int dayCount;   //여행일정에서 몇번째 날인지ex)첫번째날 두번째날

    @Column(name="day_Index")
    private int dayIndex;    //여행일정에서 각 시간별 영역의 인덱스 값

    @Column(name="day_title")
    private String dayTitle;

    @Column(name="day_region1")
    private String dayRegion1;

    @Column(name="day_region2")
    private String dayRegion2;

    @Column(name="day_Image")   //이미지 경로
    private String dayImage;

    @Column(name="day_latitude") //위도
    private String dayLatitude;

    @Column(name="day_longitude") //경도
    private String dayLongitude;

    @Column(name="day_user_id")
    private String dayUserId;




}