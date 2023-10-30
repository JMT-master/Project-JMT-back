package com.jmt.dto;

import com.jmt.entity.DayFormatEntity;
import com.jmt.entity.TravelScheduleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayFormatDto {

    private String dayId;

    private String dayTravelId;

    private List<DayForm> dayForm;

    private int dayCount;

    private String dayTitle;

    private String dayRegion1;

    private String dayRegion2;

    private int dayIndex;

    private String dayImage;

    public static DayFormatEntity toEntity(final String dto,final DayForm dayForm, TravelScheduleEntity travelScheduleEntity){

        try{
            return DayFormatEntity.builder()
                    .dayId(dto)
//                    .dayTravelId(dto.getDayTravelId())
                    .dayTravelId(travelScheduleEntity)
                    .dayCount(dayForm.getDayCount())
                    .dayIndex(dayForm.getDayIndex())
                    .dayImage(dayForm.getDayImage())
                    .dayTitle(dayForm.getDayTitle())
                    .dayRegion1(dayForm.getDayRegion1())
                    .dayRegion2(dayForm.getDayRegion2())
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static DayFormatDto toDto(final DayFormatEntity entity){
        try{
            return DayFormatDto.builder()
                    .dayId(entity.getDayId())
                    .dayTravelId(String.valueOf(entity.getDayTravelId()))
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}

