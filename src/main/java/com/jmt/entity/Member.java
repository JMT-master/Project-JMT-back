package com.jmt.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table
public class Member {

    @Id
    @Column(name = "userid", unique = true, nullable = false)
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String userid;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String passwordChk;

    @Column(length = 10, nullable = false)
    private String zipcode;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(length = 150, nullable = false)
    private String addressDetail;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column()
    private String email;

    @Column(length = 1 , nullable = false)
    private String adminYn;

    public void changeMember(Member member) {
        userid = member.getUserid();
        username = member.getUsername();
        password = member.getPassword();
        passwordChk = member.getPasswordChk();
        zipcode = member.getZipcode();
        address = member.getAddress();
        addressDetail = member.getAddressDetail();
        phone = member.getPhone();
        email = member.getEmail();
        adminYn = member.getAdminYn();
    }
}
