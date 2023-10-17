package com.jmt.qna.entity;

import com.jmt.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Builder
public class QnaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private String id;

    @Column(nullable = false)
    private String qnaTitle;

    @Column(nullable = false)
    private String qnaContent;

    @ColumnDefault("0")
    private int qnaView;

    @Column(nullable = false)
    private String qnaCategory;
}
