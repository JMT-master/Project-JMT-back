package com.jmt.entity;

import lombok.*;
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
public class Notice {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private Integer noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_userid", referencedColumnName = "userid", nullable = false)
    private Member member;

    @Column(length = 50, nullable = false)
    private String noticeCategory;

    @Column(nullable = false)
    private String noticeTitle;

    @Column(length = 6500, nullable = false)
    private String noticeContent;

    @Column(nullable = false)
    private String noticeFileKey;

}
