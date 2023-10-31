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

//    private List<DayForm> dayForm;

    private int dayCount;

    private String dayTitle;

    private String dayRegion1;

    private String dayRegion2;

    private int dayIndex;

    private String dayImage;

    public static DayFormatEntity toEntity(final DayFormatDto dto, TravelScheduleEntity travelId){

        try{
            return DayFormatEntity.builder()
//                    .dayTravelId(dto.getDayTravelId())
                    .dayTravelId(travelId)
                    .dayCount(dto.getDayCount())
                    .dayIndex(dto.getDayIndex())
                    .dayImage(dto.getDayImage())
                    .dayTitle(dto.getDayTitle())
                    .dayRegion1(dto.getDayRegion1())
                    .dayRegion2(dto.getDayRegion2())
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
                    .dayImage(entity.getDayImage())
                    .dayIndex(entity.getDayIndex())
                    .dayCount(entity.getDayCount())
                    .dayTitle(entity.getDayTitle())
                    .dayRegion1(entity.getDayRegion1())
                    .dayRegion2(entity.getDayRegion2())
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}

