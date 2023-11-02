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

    private int dayCount;

    private String dayTitle;

    private String dayRegion1;

    private String dayRegion2;

    private int dayIndex;

    private String dayImage;

    private String dayLatitude;

    private String dayLongitude;

    private String dayUserId;

    public static DayFormatEntity toEntity(final DayFormatDto dto, TravelScheduleEntity travelId,String userid){

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
                    .dayLatitude(dto.getDayLatitude())
                    .dayLongitude(dto.getDayLongitude())
                    .dayUserId(userid)
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static DayFormatDto toDto(final DayFormatEntity entity,String dayTravelId){
        try{
            return DayFormatDto.builder()
                    .dayId(entity.getDayId())
                    .dayTravelId(dayTravelId)
                    .dayImage(entity.getDayImage())
                    .dayIndex(entity.getDayIndex())
                    .dayCount(entity.getDayCount())
                    .dayTitle(entity.getDayTitle())
                    .dayRegion1(entity.getDayRegion1())
                    .dayRegion2(entity.getDayRegion2())
                    .dayLatitude(entity.getDayLatitude())
                    .dayLongitude(entity.getDayLongitude())
                    .dayUserId(entity.getDayUserId())
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}

