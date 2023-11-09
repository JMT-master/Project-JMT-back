package com.jmt.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "notice")
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String noticeId;

    @Column(name = "notice_num")
    private Long noticeIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_userid", referencedColumnName = "userid")
    private Member member;

    @Column(name = "notice_view")
    @ColumnDefault("0")
    private int noticeView; // 조회수

    @Column(length = 50, nullable = false)
    private String noticeCategory;

    @Column(nullable = false)
    private String noticeTitle;

    @Column(length = 6500, nullable = false)
    private String noticeContent;

    @Column(name = "notice_file_key") // kn_01_3_셔츠6.jpg
    private String noticeFileKey; // 파일 업로드 유무??? => 파일키 추후 작업

}
