package com.jmt.service;

import com.jmt.dto.KnowledgeDto;
import com.jmt.entity.KnowledgeEntity;
import com.jmt.entity.Member;
import com.jmt.repository.KnowledgeRepository;
import com.jmt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

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


    // 전체 지식in List 뽑기
    public List<KnowledgeDto> allKnowledgeList() {
        List<KnowledgeEntity> entities = knowledgeRepository.findAll();

        List<KnowledgeDto> result = entities.stream().map(data -> KnowledgeDto.toDto(data)).collect(Collectors.toList());

        System.out.println("result = " + result);

        return result;
    }

    //
    public Long create(KnowledgeDto knowledgeDto, @CookieValue String userid) {
        Member member = memberRepository.findById(userid).orElseThrow(EntityNotFoundException::new);
        KnowledgeEntity knowledgeEntity = KnowledgeEntity.createKnowledgeEntity(member, knowledgeDto);

        Long colNum = knowledgeRepository.countByNum();

        System.out.println("colNum = " + colNum);

        knowledgeEntity.setNum(colNum + 1);

        // 저장
        KnowledgeEntity save = knowledgeRepository.save(knowledgeEntity);
        return save.getId();
    }

}
