package com.jmt.dto;

import com.jmt.entity.DayFormatEntity;
import com.jmt.entity.TravelScheduleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayFormatDto {

    private int dayId;

    private int dayTravelId;

    private int dayCount;

    private int dayIndex;

    private String dayImage;

    public static DayFormatEntity toEntity(final DayFormatDto dto){

        try{
            return DayFormatEntity.builder()
                    .dayId(dto.getDayId())
//                    .dayTravelId(dto.getDayTravelId())
                    .dayCount(dto.getDayCount())
                    .dayIndex(dto.getDayIndex())
                    .dayImage(dto.getDayImage())
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static DayFormatDto toDto(final DayFormatEntity entity){
        try{
            return DayFormatDto.builder()
                    .dayId(entity.getDayId())
//                    .dayTravelId(entity.getDayTravelId())
                    .dayCount(entity.getDayCount())
                    .dayIndex(entity.getDayIndex())
                    .dayImage(entity.getDayImage())
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
