package com.jmt.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table
public class MemberFile {
    @Id
    private Integer fileId; // 고유 아이디

    @Column(nullable = false)
    private String fileName; // 파일 이름

    @Column(nullable = false)
    private Long fileSize; // 파일 크기

    @Column(nullable = false)
    private String fileServerPath; // 서버 Path

    @Column(nullable = false)
    private String fileCategory;

    @Comment("파일이 포함된 게시글 아이디")
    @Column(nullable = false)
    private String fileCommonId;
}
