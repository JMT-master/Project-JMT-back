package com.jmt.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseTimeEntity{
    @Id
    @Column(name = "review_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String reviewId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_userid", referencedColumnName = "userid")
    private Member member;
    @Column(name = "review_num")
    private Long reviewIdx;

    private String reviewContentid;
    @Column(length = 6500)
    private String reviewContent;


    @Column(length = 6500, columnDefinition = "int default 0")
    private int reviewLike;

    @Column(length = 6500)
    private String reviewImage;
}
