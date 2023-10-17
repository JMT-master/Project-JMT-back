package com.jmt.entity;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class MemberEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private String userid;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String passwordCheck;

    @Column
    private String zipcode;

    @Column
    private String address;

    @Column
    private String addressDetail;

    @Column
    private String adminYn;

}
