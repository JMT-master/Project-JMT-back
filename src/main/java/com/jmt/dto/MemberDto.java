package com.jmt.dto;

import com.jmt.entity.Member;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private Long userid;

    private String username;
    private String email;

    private String password;
    private String passwordChk;

    private String zipcode;
    private String address;
    private String addressDetail;

    private String phone;

    private String adminYn;
    private String socialYn;
    private String socialToken;

    public MemberDto(Member member) {
        this.userid = member.getUserid();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.passwordChk = member.getPasswordChk();
        this.zipcode = member.getZipcode();
        this.address = member.getAddress();
        this.addressDetail = member.getAddressDetail();
        this.phone = member.getPhone();
        this.adminYn = member.getAdminYn();
        this.socialYn = member.getSocialYn();
    }

    // Dto -> Entity
    public static Member toEntity(final MemberDto dto) {
        try {
            return Member.builder()
                    .userid(dto.getUserid())
                    .username(dto.getUsername())
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .passwordChk(dto.getPasswordChk())
                    .zipcode(dto.getZipcode())
                    .address(dto.getAddress())
                    .addressDetail(dto.getAddressDetail())
                    .phone(dto.getPhone())
                    .adminYn(dto.getAdminYn())
                    .socialYn(dto.getSocialYn())
                    .socialToken(dto.getSocialToken())
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
                    .email(entity.getEmail())
                    .password(entity.getPassword())
                    .passwordChk(entity.getPasswordChk())
                    .zipcode(entity.getZipcode())
                    .address(entity.getAddress())
                    .addressDetail(entity.getAddressDetail())
                    .phone(entity.getPhone())
                    .adminYn(entity.getAdminYn())
                    .socialYn(entity.getSocialYn())
                    .socialToken(entity.getSocialToken())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
