package com.jmt.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Travel {

    private LocalDateTime startTime;
    private String place;
    private String placeName;
    //    private String imageUrl;
}

