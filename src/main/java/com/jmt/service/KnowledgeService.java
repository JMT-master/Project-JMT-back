package com.jmt.service;

import com.jmt.constant.Board;
import com.jmt.dto.KnowledgeAnswerDto;
import com.jmt.dto.KnowledgeDto;
import com.jmt.dto.KnowledgeSendDto;
import com.jmt.entity.KnowledgeAnswerEntity;
import com.jmt.entity.KnowledgeEntity;
import com.jmt.entity.Member;
import com.jmt.entity.MemberFile;
import com.jmt.repository.KnowledgeAnswerRepository;
import com.jmt.repository.KnowledgeRepository;
import com.jmt.repository.MemberFileRepository;
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
    private final MemberFileRepository memberFileRepository;
    private final KnowledgeAnswerRepository knowledgeAnswerRepository;
    private final FileService fileService;

    // 중복 제거 계산
    private List<KnowledgeEntity> distinctNumList(List<KnowledgeEntity> revList) {
        int flag = 0;
        List<KnowledgeEntity> sendList = new ArrayList<>();

        sendList.add(revList.get(revList.size()-1));
        for(int i=revList.size()-1; i >= 0; i--) {
            flag = 0;
            for(KnowledgeEntity data : sendList) {
                if(revList.get(i).getNum().equals(data.getNum())) {
                    continue;
                }
                flag++;
            }

            if(flag == sendList.size()) sendList.add(revList.get(i));
        }

        return sendList;
    }

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

    // 지식in 전체/관광지/음식/숙박 category
    public List<KnowledgeDto> categoryKnowledgeList(String category) {
        int flag = 0;
        List<KnowledgeEntity> byCategory = new ArrayList<>();
        if(category.equals("관광지") || category.equals("음식") || category.equals("숙박")) {
            List<KnowledgeEntity> categoryLists = knowledgeRepository.findByCategory(category);

            // 카테고리 결과가 없을 경우
            if(categoryLists.isEmpty()){
                List<KnowledgeDto> result = new ArrayList<>();
                return result;
            }

            byCategory = distinctNumList(categoryLists);

        } else if(category.equals("전체")) {
            List<Long> numList = knowledgeRepository.distinctBynum();
            byCategory = numList.stream().map(data -> knowledgeRepository.findByNum(data).get(0)).collect(Collectors.toList());
        } else {
            return null;
        }

        return byCategory.stream().map(KnowledgeDto::toDto).collect(Collectors.toList());
    }

    // 제목, 내용별 검색 결과 반환
    // select : 제목/내용, searchResult : 입력한 검색어
    public List<KnowledgeDto> search(String select, String searchResult) {
        List<KnowledgeEntity> result = new ArrayList<>();
        List<KnowledgeEntity> distinctresult = new ArrayList<>();

        if(select.equals("title")) result = knowledgeRepository.findByTitleContaining(searchResult);
        else                       result = knowledgeRepository.findByContentContaining(searchResult);

        if(result.isEmpty()) {
            List<KnowledgeDto> emptyResult = new ArrayList<>();
            return emptyResult;
        }

        distinctresult = distinctNumList(result);

        return distinctresult.stream().map(KnowledgeDto::toDto).collect(Collectors.toList());
    }


    // 지식in Detail List
    public List<KnowledgeSendDto> writeNumKnowledgeList(KnowledgeDto knowledgeDto, Long num) {
        Member member = memberRepository.findByEmail(knowledgeDto.getUserid()).get();
        List<KnowledgeEntity> entities = knowledgeRepository.findByUseridAndNum(member, num);

        System.out.println("entities = " + entities);

        List<KnowledgeSendDto> result = new ArrayList<>();
        for(KnowledgeEntity entity : entities) {
            entity.setView(entity.getView() + 1); // 조회수 1 추가
            knowledgeRepository.save(entity);
            KnowledgeSendDto dto = KnowledgeSendDto.toDto(entity);

            if(entity.getFileKey() != null) {
                MemberFile memberFile = memberFileRepository.findById(entity.getFileKey()).get();
                dto.setServerPath(memberFile.getFileServerPath());
                dto.setOriginalName(memberFile.getFileName());
            }

            result.add(dto);
        }

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

    // 지식인 답글 리스트
    public List<KnowledgeAnswerDto> readAnswer(Long num) {
        List<KnowledgeAnswerEntity> resultEntity = knowledgeAnswerRepository.findByKnNumOrderByModDateDesc(num);

        return resultEntity.stream().map(KnowledgeAnswerDto::toDto).collect(Collectors.toList());
    }

    // 지식인 답글 작성
    public List<KnowledgeAnswerDto> createAnswer(KnowledgeAnswerDto knowledgeAnswerDto, String userid) {
        KnowledgeAnswerEntity entity = KnowledgeAnswerDto.toEntity(knowledgeAnswerDto);
        entity.setAnswerWriter(userid);

        System.out.println("entity.getAnswerWriter() = " + entity.getAnswerWriter());
        System.out.println("entity.getContent() = " + entity.getContent());
        System.out.println("knowledgeAnswerDto = " + knowledgeAnswerDto.getContent());
        System.out.println("entity empty" + entity.getContent().replaceAll(" ",""));
        System.out.println("entity empty true/false" + entity.getContent().replaceAll(" ","").isEmpty());
        try {
            if ((entity.getAnswerWriter() == null) || entity.getAnswerWriter().equals("anonymousUser") ||
                    entity.getContent() == null || entity.getContent().replaceAll(" ","").isEmpty()) {
                System.out.println("들어옴???");
                return null;

            }
        } catch (NullPointerException e) {
            throw new RuntimeException("null 에러");
        }

        KnowledgeAnswerEntity save = knowledgeAnswerRepository.save(entity);
        List<KnowledgeAnswerEntity> byKnNum = knowledgeAnswerRepository.findByKnNumOrderByModDateDesc(save.getKnNum());

        return byKnNum.stream().map(KnowledgeAnswerDto::toDto).collect(Collectors.toList());

    }

    // 지식인 답글 좋아요 1 증가
    public List<KnowledgeAnswerDto> likeAddAnswer(KnowledgeAnswerDto knowledgeAnswerDto) {
        KnowledgeAnswerEntity knowledgeAnswerEntity = knowledgeAnswerRepository.findByKnNumAndAnswerWriterAndContent(
                knowledgeAnswerDto.getKnNum(),
                knowledgeAnswerDto.getAnswerWriter(),
                knowledgeAnswerDto.getContent()
        ).orElseThrow(EntityNotFoundException::new);

        int answerLike = knowledgeAnswerEntity.getAnswerLike();
        knowledgeAnswerEntity.setAnswerLike(answerLike+1);

        KnowledgeAnswerEntity save = knowledgeAnswerRepository.save(knowledgeAnswerEntity);

        List<KnowledgeAnswerEntity> resultEntity = knowledgeAnswerRepository.findByKnNumOrderByModDateDesc(save.getKnNum());

        return resultEntity.stream().map(KnowledgeAnswerDto::toDto).collect(Collectors.toList());
    }

}
