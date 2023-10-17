package com.jmt.knowledge;

import com.jmt.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "knowledge")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private String knId;

    // MemberEntity 연동
    @Column
    private String knUserid;

    @Column(nullable = false)
    private String knTitle;

    @Column(nullable = false)
    private String knContent;

    @Column(name = "kn_regDate")
    private Date knRegDate;

    @Column(name = "kn_modDate")
    private Date knmodDate;

    @Column
    @ColumnDefault("0")
    private int knView;

    @Column
    private String knFileKey;

}
