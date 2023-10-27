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
    private List<Travel> travels;
}
