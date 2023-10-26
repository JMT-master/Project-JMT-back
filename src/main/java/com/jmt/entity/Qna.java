package com.jmt.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Builder
public class Qna extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "qna_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_userid", referencedColumnName = "userid")
    private Member member;

    @Column
    private Long qnaNum;

    @Column(nullable = false)
    private String qnaTitle;

    @Column(length = 6500, nullable = false)
    private String qnaContent;

    @ColumnDefault("0")
    private int qnaView;

    @Column(length =  50)
    private String qnaCategory;

    @Column
    private String qnaFileKey;

}
