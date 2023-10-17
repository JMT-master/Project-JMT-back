package com.jmt.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table
public class Member {

    @Id
    @Column(name = "userid")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String userid;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String passwordCheck;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(length = 10, nullable = false)
    private String zipcode;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(length = 150, nullable = false)
    private String addressDetail;

    @Column(length = 1 , nullable = false)
    private String adminYn;


}
