package com.jmt.dto;

import com.jmt.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private String username;

    private String nickname;

    private String password;

    private String phone;

    private String zipcode;

    private String address;

    private String addressDetail;

    private String imgUrl;

    private String info;

    private String adminYn;

    public MemberDto(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.phone = member.getPhone();
        this.zipcode = member.getZipcode();
        this.address = member.getAddress();
        this.addressDetail = member.getAddressDetail();
        this.adminYn = member.getAdminYn();
    }

    public Member toEntity(final MemberDto dto) {
        try {
            return Member.builder()
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .passwordCheck(dto.getPassword())
                    .phone(dto.getPhone())
                    .zipcode(dto.getZipcode())
                    .address(dto.getAddress())
                    .addressDetail(dto.getAddressDetail())
                    .adminYn(dto.getAdminYn())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
