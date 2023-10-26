package com.jmt.entity;

import lombok.*;

import java.util.List;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Travel {
    private List<TravelPlan> travelPlans;
}

