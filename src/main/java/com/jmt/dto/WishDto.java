package com.jmt.dto;

import com.jmt.entity.TravelScheduleEntity;
import com.jmt.entity.WishEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishDto {

    private String wishId;

    private String wishUserId;

    private String wishTravelId;

    private String wishImg;

    private String wishTitle;

    private String wishGubun;//값이 tdn(travel destination)이면 여행지 / 값이 tps(travel plans) 이면 여행일정

    private String wishApiId; //api로 받아오는 데이터의 키값 ex)CNTS_300000000015869(nullable)

    private String address;

    private String phoneno;

    private String content;

    private String tag;

    public static WishEntity toEntity(final WishDto dto, TravelScheduleEntity travelId){
        try{
            return WishEntity.builder()
                    .wishTravelId(travelId)
                    .wishApiId(dto.getWishApiId())
                    .wishUserId(dto.getWishUserId())
                    .wishImg(dto.getWishImg())
                    .wishTitle(dto.getWishTitle())
                    .wishGubun(dto.getWishGubun())
                    .address(dto.getAddress())
                    .phoneno(dto.getPhoneno())
                    .content(dto.getContent())
                    .tag(dto.getTag())
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static WishDto toDto(final WishEntity entity, String wishTravelId){
        try{
            return WishDto.builder()
                    .wishId(entity.getWishId())
                    .wishTravelId(wishTravelId)
                    .wishApiId(entity.getWishApiId())
                    .wishUserId(entity.getWishUserId())
                    .wishImg(entity.getWishImg())
                    .wishTitle(entity.getWishTitle())
                    .wishGubun(entity.getWishGubun())
                    .address(entity.getAddress())
                    .phoneno(entity.getPhoneno())
                    .content(entity.getContent())
                    .tag(entity.getTag())
                    .build();
        } catch (Exception e){
                    throw new RuntimeException(e.getMessage());
        }
    }

}

