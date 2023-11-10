package com.jmt.service;

import com.jmt.dto.WishDto;
import com.jmt.entity.TravelScheduleEntity;
import com.jmt.entity.WishEntity;
import com.jmt.repository.TravelScheduleRepository;
import com.jmt.repository.WishReopsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishService {

    private final TravelScheduleRepository travelScheduleRepository;

    private final WishReopsitory wishReopsitory;

    public int wishTdnInsert(WishDto dto,String userId){
        int result = 1;

        try{
            String wishApiId = wishReopsitory.wishApiId(dto.getWishApiId());
            System.out.println("wishApiId-----------"+wishApiId);
            if(wishApiId == null || wishApiId == ""){
                dto.setWishUserId(userId);
                //여행지에서 찜 등록할 땐 travelId(여행일정pk)가 없어도 상관없기에 null로 넘김
                wishReopsitory.save(WishDto.toEntity(dto,null));
            }else{
                System.out.println("wishApiId가 이미 있음");
            }
        }catch (Exception e){
            result = 0;
            e.printStackTrace();
        }
        return result;
    }

    public int wishTpsInsert(WishDto dto,String userId){
        int result = 1;
        TravelScheduleEntity travelId = travelScheduleRepository.findByTravelId(dto.getWishTravelId());
        String wishTravelId = wishReopsitory.wishTravelId(dto.getWishTravelId(),userId);
        try{
            if(wishTravelId == null || wishTravelId == ""){
                dto.setWishUserId(userId);
                wishReopsitory.save(WishDto.toEntity(dto,travelId));
            }else{
                System.out.println("wishTravelId가 이미 있음");
            }
        }catch (Exception e){
            result = 0;
            e.printStackTrace();
        }
        return result;
    }

    public int wishTdnDelete(WishDto dto){

        int result = 1;
        try{
            WishEntity wishId = wishReopsitory.findByWishId(dto.getWishId());
            wishReopsitory.delete(wishId);
        }catch (Exception e){
            result = 0;
            e.printStackTrace();
        }
        return result;

    }

    public void wishTpsDelete(WishDto dto){
        try {
            wishReopsitory.wishDrop(dto.getWishId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<WishDto> wishTdnSelect(String userId){

            List<WishEntity> select = wishReopsitory.wishTdnSelect(userId);

            List<WishDto> result = select.stream().map(data -> WishDto.toDto(data, null)).collect(Collectors.toList());

        return result;

    }

    public List<WishDto> wishTpsSelect(String userId){

        List<WishDto> result = null;

        List<WishEntity> select = wishReopsitory.wishTpsSelect(userId);

        for(int i=0; i<select.size(); i++){
            String travleId = select.get(i).getWishTravelId().getTravelId();
            result = select.stream().map(data -> WishDto.toDto(data, travleId)).collect(Collectors.toList());
        }

        return result;

    }
}
