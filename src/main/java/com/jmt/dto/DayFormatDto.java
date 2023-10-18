package com.jmt.dto;

import com.jmt.entity.TravelScheduleEntity;
import lombok.Data;

@Data
public class DayFormatDto {

    private String dayId;

    private TravelScheduleEntity dayTravelId;

    private int daySelect;

    private int dayIndex;

    private int dayImage;
}
