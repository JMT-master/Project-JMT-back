package com.jmt.dto;

import com.jmt.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private String username;


    private String password;

    private String phone;

    private String zipcode;

    private String address;

    private String addressDetail;

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

    public static Member toEntity(final MemberDto dto) {
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

    public static MemberDto toDto(final Member entity) {
        try {
            return MemberDto.builder()
                    .username(entity.getUsername())
                    .phone(entity.getPhone())
                    .zipcode(entity.getZipcode())
                    .address(entity.getAddress())
                    .addressDetail(entity.getAddressDetail())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
