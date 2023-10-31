package com.jmt.dto;

import com.jmt.entity.TravelScheduleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelScheduleDto {

    private String travelId;

    private String travelUserId;

    private String travelTitle;

    private String travelYn;

    private int travelPnum;

    private LocalDateTime travelStartDate;

    private LocalDateTime travelEndDate;

    private String travelStartTime;

    private String travelEndTime;


    public static TravelScheduleEntity toEntity(final TravelScheduleDto dto){
        try{
            return TravelScheduleEntity.builder()
                    .travelUserId(dto.getTravelUserId())
                    .travelTitle(dto.getTravelTitle())
                    .travelYn(dto.getTravelYn())
                    .travelPnum(dto.getTravelPnum())
                    .travelStartDate(dto.getTravelStartDate())
                    .travelEndDate(dto.getTravelEndDate())
                    .travelStartTime(dto.getTravelStartTime())
                    .travleEndTime(dto.getTravelEndTime())
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static TravelScheduleDto toDto(final TravelScheduleEntity entity){
        try{
            return TravelScheduleDto.builder()
                    .travelId(entity.getTravelId())
                    .travelUserId(entity.getTravelUserId())
                    .travelTitle(entity.getTravelTitle())
                    .travelYn(entity.getTravelYn())
                    .travelPnum(entity.getTravelPnum())
                    .travelStartDate(entity.getTravelStartDate())
                    .travelEndDate(entity.getTravelEndDate())
                    .travelStartTime(entity.getTravelStartTime())
                    .travelEndTime(entity.getTravleEndTime())
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}



