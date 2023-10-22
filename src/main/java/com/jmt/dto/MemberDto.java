package com.jmt.dto;

import com.jmt.entity.Member;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private String userid;

    private String username;

    private String password;
    private String passwordChk;

    private String zipcode;
    private String address;
    private String addressDetail;

    private String phone;

    private String adminYn;

    public MemberDto(Member member) {
        this.userid = member.getUserid();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.passwordChk = member.getPasswordChk();
        this.zipcode = member.getZipcode();
        this.address = member.getAddress();
        this.addressDetail = member.getAddressDetail();
        this.phone = member.getPhone();
        this.adminYn = member.getAdminYn();
    }

    // Dto -> Entity
    public static Member toEntity(final MemberDto dto) {
        try {
            return Member.builder()
                    .userid(dto.getUserid())
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .passwordChk(dto.getPasswordChk())
                    .zipcode(dto.getZipcode())
                    .address(dto.getAddress())
                    .addressDetail(dto.getAddressDetail())
                    .phone(dto.getPhone())
                    .adminYn(dto.getAdminYn())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // entity -> Dto
    public static MemberDto toDto(final Member entity) {
        try {
            return MemberDto.builder()
                    .userid(entity.getUserid())
                    .username(entity.getUsername())
                    .password(entity.getPassword())
                    .passwordChk(entity.getPasswordChk())
                    .zipcode(entity.getZipcode())
                    .address(entity.getAddress())
                    .addressDetail(entity.getAddressDetail())
                    .phone(entity.getPhone())
                    .adminYn(entity.getAdminYn())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
