package com.jmt.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TravelScheduleDto {

    private String travelId;

    private String travelUserid;

    private String travelTitle;

    private String travelYn;

    private int travelPnum;

    private LocalDateTime travelStartDate;

    private LocalDateTime travelEndDate;

}


