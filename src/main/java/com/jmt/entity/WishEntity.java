package com.jmt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="wishList")
public class WishEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name="wish_id")
    private String wishId;

    private String wishUserId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="wish_travelId")
    private TravelScheduleEntity wishTravelId;

    @Column(name="wish_img")
    private String wishImg;

    @Column(name="wish_title")
    private String wishTitle;

    @Column(name="wish_gubun")
    private String wishGubun;

    @Column(name="wish_apiId")
    private String wishApiId;

    @Column(name="regDate")
    private LocalDateTime regDate;


}
