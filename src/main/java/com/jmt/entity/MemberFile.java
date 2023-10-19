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
public class MemberFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String fileId;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileSize;

    @Column(nullable = false)
    private String fileClientPath;

    @Column(nullable = false)
    private String fileServerPath;

    @Column(nullable = false)
    private String fileMaxSize;

    @Column(nullable = false)
    private String fileCategory;

    @Comment("파일이 포함된 게시글 아이디")
    @Column(nullable = false)
    private String fileCommonId;



}
