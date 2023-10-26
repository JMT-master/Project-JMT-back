package com.jmt.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TravelPlan {
    private LocalDateTime startTime;
    private String place;
    private String placeName;
    private String imageUrl;
}
