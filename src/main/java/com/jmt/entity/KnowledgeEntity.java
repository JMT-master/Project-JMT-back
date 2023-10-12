package com.jmt.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kn_id")
    private Long id;

    @Column(nullable = false,
    name = "kn_title")
    private String title;

    @Column(nullable = false, name = "kn_content")
    private String content;

    @Column(name = "kn_view")
    @ColumnDefault("0")
    private int view;

}
