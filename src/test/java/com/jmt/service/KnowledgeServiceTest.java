package com.jmt.service;

import com.jmt.dto.KnowledgeDto;
import com.jmt.dto.MemberDto;
import com.jmt.entity.Member;
import com.jmt.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class KnowledgeServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private KnowledgeService knowledgeService;

    @Test
    void create() {
        // ID 생성
        MemberDto memberDto = new MemberDto(
                "test","홍길동","abc@abc.com","1234","1234",
                "011111","서울시","양천구","010-2345-7891","Y","N"
        );

        MemberDto result = memberService.create(memberDto);

        Member repositoryUser = memberRepository.findById(memberDto.getUserid()).get();

        System.out.println("repositoryUser = " + repositoryUser);

        for(int i=0; i<5; i++) {
            KnowledgeDto knowledgeDto = KnowledgeDto.builder()
                    .title("test제목" + i)
                    .content("test내용" + i)
                    .category("관광지")
                    .view(0)
                    .build();

//            knowledgeService.create(knowledgeDto, repositoryUser.getEmail());
        }

    }

    @Test
    void listAll() {
        // ID 생성
        for(int i=0; i<5; i++) {
            MemberDto memberDto = new MemberDto(
                    "test"+i,"홍길동","abc@abc.com","1234","1234",
                    "011111","서울시","양천구","010-2345-789"+i,"Y","N"
            );

            MemberDto result = memberService.create(memberDto);

            Member repositoryUser = memberRepository.findById(memberDto.getUserid()).get();

            System.out.println("repositoryUser = " + repositoryUser);

            KnowledgeDto knowledgeDto = KnowledgeDto.builder()
                    .title("test제목" + i)
                    .content("test내용" + i)
                    .category("관광지")
                    .view(0)
                    .build();

//            knowledgeService.create(knowledgeDto, repositoryUser.getEmail());
        }

        List<KnowledgeDto> knowledgeDtos = knowledgeService.allKnowledgeList();



    }

}