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
    private String fileId; // kn_01_3_셔츠6.jpg

    @Column(nullable = false)
    private String fileName; // 셔츠6

    @Column(nullable = false)
    private Long fileSize; // 파일 크기

    @Column(nullable = false)
    private String fileServerPath; // C:\jmt\images

    @Column(nullable = false)
    private String fileCategory; // Board.KN

    @Comment("파일이 포함된 게시글 아이디")
    @Column(nullable = false)
    private String fileUserId; // ID

    @Comment("KN/QNA/Notice 각 키")
    @Column(nullable = false)
    private String fileInfo; // info
}
