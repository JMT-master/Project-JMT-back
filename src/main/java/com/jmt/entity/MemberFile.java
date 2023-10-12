package com.jmt.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "member_file")
public class MemberFile {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "file_userid")
    private String fileUserid;
}
