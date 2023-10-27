package com.jmt.entity;

import com.jmt.dto.KnowledgeDto;
import com.jmt.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "knowledge")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeEntity extends BaseTimeEntity {

    @Id
    @Column(name = "kn_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kn_userid", referencedColumnName = "userid", nullable = false)
    private Member userid; // member user id

    @Column(name = "kn_num")
    private Long num;

    @Column(nullable = false, name = "kn_title")
    private String title; // 제목

    @Column(nullable = false, name = "kn_content")
    private String content; // 내용

    @Column(nullable = false, name = "kn_category")
    private String category; // 구분

    @Column(name = "kn_view")
    @ColumnDefault("0")
    private int view; // 조회수

    @Column(name = "kn_file_key") // kn_01_3_셔츠6.jpg
    private String fileKey; // 파일 업로드 유무??? => 파일키 추후 작업

    public static KnowledgeEntity createKnowledgeEntity(Member member, KnowledgeDto knowledgeDto) {
        return KnowledgeEntity.builder()
                .userid(member)
                .num(knowledgeDto.getNum())
                .title(knowledgeDto.getTitle())
                .content(knowledgeDto.getContent())
                .category(knowledgeDto.getCategory())
                .view(knowledgeDto.getView())
                .build();
    }

}
