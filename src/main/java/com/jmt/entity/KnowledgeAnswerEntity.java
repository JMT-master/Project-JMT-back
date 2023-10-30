package com.jmt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "knowledge_answer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeAnswerEntity extends BaseTimeEntity {
    @Id
    @Column(name = "answer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 글번호만 mapping하면 답변을 달 수 있음
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "answer_kn_id", referencedColumnName = "id", nullable = false)
//    private KnowledgeEntity knId;
    @Column(nullable = false)
    private Long knNum;

    @Column(nullable = false)
    private String answerWriter;

    @Column(nullable = false)
    private String content;

    private int answerLike;

}
