package com.jmt.dto;

import com.jmt.entity.DayFormatEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayForm {
    private int dayCount;

    private String dayTitle;

    private String dayRegion1;

    private String dayRegion2;

    private int dayIndex;

    private String dayImage;

    public static DayForm toDto(final DayFormatEntity entity){
        try{
            return DayForm.builder()
                    .dayCount(entity.getDayCount())
                    .dayIndex(entity.getDayIndex())
                    .dayImage(entity.getDayImage())
                    .dayTitle(entity.getDayTitle())
                    .dayRegion1(entity.getDayRegion1())
                    .dayRegion2(entity.getDayRegion2())
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
