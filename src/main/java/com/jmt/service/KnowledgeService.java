package com.jmt.service;

import com.jmt.constant.Board;
import com.jmt.dto.KnowledgeDto;
import com.jmt.dto.KnowledgeSendDto;
import com.jmt.entity.KnowledgeEntity;
import com.jmt.entity.Member;
import com.jmt.repository.KnowledgeRepository;
import com.jmt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KnowledgeService {
    private final MemberRepository memberRepository;
    private final KnowledgeRepository knowledgeRepository;
    private final FileService fileService;


    // 전체 지식in List 중복 제거 후 뽑기
    public List<KnowledgeDto> allKnowledgeList() {
        List<Long> entities = knowledgeRepository.distinctBynum();

        System.out.println("entities = " + entities);

        List<KnowledgeDto> result = entities.stream()
                                            .map(data -> KnowledgeDto.toDto(knowledgeRepository.findByNum(data).get(0)))
                                            .collect(Collectors.toList());
        System.out.println("result = " + result);
        return result;
    }


    // 지식in Detail List
    public List<KnowledgeSendDto> writeNumKnowledgeList(KnowledgeDto knowledgeDto, Long num) {
        Member member = memberRepository.findByEmail(knowledgeDto.getUserid()).get();
        List<KnowledgeEntity> entities = knowledgeRepository.findByUseridAndNum(member, num);

        List<KnowledgeSendDto> result = entities.stream().map(KnowledgeSendDto::toDto).collect(Collectors.toList());

        System.out.println("result = " + result);

        return result;
    }

    // 지식인 글 작성
    public void create(List<MultipartFile> multipartFiles, KnowledgeDto knowledgeDto, String userid) {
        Member member = memberRepository.findByEmail(userid).orElseThrow(EntityNotFoundException::new);
        KnowledgeEntity knowledgeEntity = KnowledgeEntity.createKnowledgeEntity(member, knowledgeDto);

        Long num = knowledgeRepository.countByNum();
        num += 1;
        knowledgeEntity.setNum(num); // 글번호 Entity에 등록


        if(multipartFiles != null) {

            List<String> fileKeys = fileService.fileUpload(multipartFiles, userid, Board.KN, num.intValue());
            knowledgeDto.setFileKey(fileKeys);
            System.out.println("strings = " + fileKeys);
        }

        if(knowledgeDto.getFileKey() == null) {
            knowledgeRepository.save(knowledgeEntity);
        } else {
            List<KnowledgeEntity> knowledgeEntityList = new ArrayList<>();
            knowledgeDto.getFileKey().forEach(data -> {
                knowledgeEntity.setFileKey(data);

                KnowledgeEntity knowledge = KnowledgeEntity.builder()
                        .id(knowledgeEntity.getId())
                        .userid(knowledgeEntity.getUserid())
                        .num(knowledgeEntity.getNum())
                        .title(knowledgeEntity.getTitle())
                        .content(knowledgeEntity.getContent())
                        .category(knowledgeEntity.getCategory())
                        .view(knowledgeEntity.getView())
                        .fileKey(knowledgeEntity.getFileKey())
                        .build();

                knowledgeEntityList.add(knowledge);
            });

            System.out.println("knowledgeEntityList = " + knowledgeEntityList);
            knowledgeRepository.saveAll(knowledgeEntityList);
        }
    }

}
