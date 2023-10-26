package com.jmt.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TravelPlan {
    private LocalDateTime startTime = LocalDateTime.now();
    private String place = "제주도";
    private String placeName = "한라산";
    private String imageUrl;

    public static List<TravelPlan> createTravelPlans(int numberOfPlans) {
        List<TravelPlan> travelPlans = new ArrayList<>();
        for (int i = 0; i < numberOfPlans; i++) {
            TravelPlan travelPlan = new TravelPlan();
            // 이미지 URL은 나중에 설정할 수 있도록 null로 설정
            travelPlan.setImageUrl(null);
            travelPlans.add(travelPlan);
        }
        return travelPlans;
    }
}
