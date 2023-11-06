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
@Getter
@Setter
@ToString
@Table
public class Member {

    @Id
    @Column(name = "userid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private Long userid; // email

    @Column(nullable = false)
    private String email; // email

    @Column(length = 50)
    private String username; // 이름

    // password encoder로 인하여 length 변경
    @Column(length = 500)
    private String password;

    // password encoder로 인하여 length 변경
    @Column(length = 500)
    private String passwordChk;

    @Column(length = 10)
    private String zipcode;

    @Column(length = 150)
    private String address;

    @Column(length = 150)
    private String addressDetail;

    @Column(length = 20, unique = true)
    private String phone;

    @Column(length = 1 )
    private String adminYn;

    @Column(length = 1 )
    private String socialYn;

    @Column(length = 500)
    private String socialToken;

    public void changeMember(Member member) {
        username = member.getUsername();
        password = member.getPassword();
        passwordChk = member.getPasswordChk();
        zipcode = member.getZipcode();
        address = member.getAddress();
        addressDetail = member.getAddressDetail();
        phone = member.getPhone();
        adminYn = member.getAdminYn();
    }
}
