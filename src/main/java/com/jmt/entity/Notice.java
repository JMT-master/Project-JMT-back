package com.jmt.entity;

import com.sun.istack.NotNull;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "notice_file_id", referencedColumnName = "file_id",nullable = true),
            @JoinColumn(name = "notice_file_userid", referencedColumnName = "file_userid",nullable = true)
    })
    private MemberFile memberFile;

    private String noticeCategory;

    @NotNull
    private String noticeTitle;

    @Column(length = 6500, nullable = false)
    private String noticeContent;

}
