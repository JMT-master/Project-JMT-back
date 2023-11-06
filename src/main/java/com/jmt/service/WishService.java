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
            dto.setWishUserId(userId);
            //여행지에서 찜 등록할 땐 travelId(여행일정pk)가 없어도 상관없기에 null로 넘김
            wishReopsitory.save(WishDto.toEntity(dto,null));
        }catch (Exception e){
            result = 0;
            e.printStackTrace();
        }
        return result;
    }

    public int wishTpsInsert(WishDto dto,String userId){
        int result = 1;
        TravelScheduleEntity travelId = travelScheduleRepository.findByTravelId(dto.getWishTravelId());
        try{
            dto.setWishUserId(userId);
            //여행지에서 찜 등록할 땐 travelId(여행일정pk)가 없어도 상관없기에 null로 넘김
            wishReopsitory.save(WishDto.toEntity(dto,travelId));
        }catch (Exception e){
            result = 0;
            e.printStackTrace();
        }
        return result;
    }



    public List<WishDto> wishTdnSelect(String userId){

            List<WishEntity> select = wishReopsitory.wishTdnSelect(userId);

            List<WishDto> result = select.stream().map(data -> WishDto.toDto(data, null)).collect(Collectors.toList());

        return result;

    }

    public List<WishDto> wishTpsSelect(String userId){

        List<WishEntity> select = wishReopsitory.wishTpsSelect(userId);

        select.get(0).getWishTravelId().getTravelId();
        List<WishDto> result = select.stream().map(data -> WishDto.toDto(data, null)).collect(Collectors.toList());

        return result;

    }
}
