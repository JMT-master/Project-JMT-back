package com.jmt.entity;

import com.jmt.entity.BaseTimeEntity;
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
public class QnaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "qna_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_userid", referencedColumnName = "userid", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String qnaTitle;

    @Column(length = 6500, nullable = false)
    private String qnaContent;

    @ColumnDefault("0")
    private int qnaView;

    @Column(length =  50, nullable = false)
    private String qnaCategory;

    @Column(nullable = false)
    private String qnaFileKey;

}
