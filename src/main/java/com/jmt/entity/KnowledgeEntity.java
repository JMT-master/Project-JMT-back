package com.jmt.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "knowledge")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeEntity extends BaseTimeEntity {

    @Id
    @Column(name = "kn_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false,
    name = "kn_title")
    private String title;

    @Column(nullable = false, name = "kn_content")
    private String content;

    @Column(name = "kn_view")
    @ColumnDefault("0")
    private int view;

}
