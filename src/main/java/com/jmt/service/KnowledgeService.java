package com.jmt.service;

import com.jmt.constant.Board;
import com.jmt.dto.KnowledgeAnswerDto;
import com.jmt.dto.KnowledgeDto;
import com.jmt.dto.KnowledgeSendDto;
import com.jmt.dto.KnowledgeUpdateDto;
import com.jmt.entity.KnowledgeAnswerEntity;
import com.jmt.entity.KnowledgeEntity;
import com.jmt.entity.Member;
import com.jmt.entity.MemberFile;
import com.jmt.repository.KnowledgeAnswerRepository;
import com.jmt.repository.KnowledgeRepository;
import com.jmt.repository.MemberFileRepository;
import com.jmt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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

        List<KnowledgeEntity> entities = knowledgeRepository.findAll(Sort.by(Sort.Direction.DESC, "num"));

        return entities.stream().map(KnowledgeDto::toDto).collect(Collectors.toList());
    }

    // 지식in 전체/관광지/음식/숙박 category
    public List<KnowledgeDto> categoryKnowledgeList(String category) {
        int flag = 0;
        List<KnowledgeEntity> byCategory = new ArrayList<>();
        if(category.equals("관광지") || category.equals("음식") || category.equals("숙박")) {
            byCategory = knowledgeRepository.findByCategoryOrderByNumDesc(category);

            // 카테고리 결과가 없을 경우
            if(byCategory.isEmpty()){
                List<KnowledgeDto> result = new ArrayList<>();
                return result;
            }
        } else if(category.equals("전체")) {
            byCategory = knowledgeRepository.findAll(Sort.by(Sort.Direction.DESC, "num"));
        } else {
            return null;
        }

        return byCategory.stream().map(KnowledgeDto::toDto).collect(Collectors.toList());
    }

    // 제목, 내용별 검색 결과 반환
    // select : 제목/내용, searchResult : 입력한 검색어
    public List<KnowledgeDto> search(String select, String searchResult) {
        List<KnowledgeEntity> result = new ArrayList<>();

        if(select.equals("title")) result = knowledgeRepository.findByTitleContaining(searchResult);
        else                       result = knowledgeRepository.findByContentContaining(searchResult);

        if(result.isEmpty()) {
            List<KnowledgeDto> emptyResult = new ArrayList<>();
            return emptyResult;
        }

        return result.stream().map(KnowledgeDto::toDto).collect(Collectors.toList());
    }


    // 지식in Detail List
    public List<KnowledgeSendDto> detailForm(Long num) {
        // 작성한 글 가져오기
        KnowledgeEntity knowledgeEntity = knowledgeRepository.findByNum(num);

        List<KnowledgeSendDto> result = new ArrayList<>();

        // 조회수 1 추가
        knowledgeEntity.setView(knowledgeEntity.getView() + 1);

        // 파일이 있을 경우
        if(knowledgeEntity.getFileKey() != null) {
            List<MemberFile> memberFiles = memberFileRepository.findByFileInfo(knowledgeEntity.getFileKey());
            memberFiles.stream().forEach(memberFile -> {
                KnowledgeSendDto dto = KnowledgeSendDto.toDto(knowledgeEntity);
                dto.setServerPath(memberFile.getFileServerPath());
                dto.setOriginalName(memberFile.getFileName());
                dto.setView(knowledgeEntity.getView());
                result.add(dto);
            });
        } else {
            KnowledgeSendDto dto = KnowledgeSendDto.toDto(knowledgeEntity);
            dto.setView(knowledgeEntity.getView());
            result.add(dto);
        }

        knowledgeRepository.save(knowledgeEntity);

        return result;
    }

    // 지식인 글 작성
    public void create(List<MultipartFile> multipartFiles, KnowledgeDto knowledgeDto, String userid) {
        Member member = memberRepository.findByEmailAndSocialYn(userid,knowledgeDto.getSocialYn()).orElseThrow(EntityNotFoundException::new);
        KnowledgeEntity knowledgeEntity = KnowledgeEntity.createKnowledgeEntity(member, knowledgeDto);

        Optional<Long> l = knowledgeRepository.countByNum();
        Long num = 0L;
        if(l.isPresent())  num = l.get();

        num += 1;
        knowledgeEntity.setNum(num); // 글번호 Entity에 등록

        if(multipartFiles != null) {
            String fileKey = fileService.fileUpload(multipartFiles, userid, Board.KN, num.intValue());
            knowledgeEntity.setFileKey(fileKey);
        }

        knowledgeRepository.save(knowledgeEntity);
    }

    // 지식인 글 수정
    public void updateKnowledge(KnowledgeUpdateDto knowledgeUpdateDto, String userid) {
        Member member = memberRepository.findByEmailAndSocialYn(userid,knowledgeUpdateDto.getSocialYn()).orElseThrow(EntityNotFoundException::new);
        KnowledgeEntity knowledgeEntity = knowledgeRepository.findByUseridAndNum(member, knowledgeUpdateDto.getNum())
                .orElseThrow(EntityNotFoundException::new);

        if(!knowledgeEntity.getFileKey().isEmpty()) {
            List<MemberFile> memberFiles = memberFileRepository.findByFileInfo(knowledgeEntity.getFileKey());

            List<MemberFile> deleteFiles = memberFiles.stream().filter(data -> !knowledgeUpdateDto.getFiles().contains(data.getFileName()))
                    .collect(Collectors.toList());

            memberFileRepository.deleteAll(deleteFiles);
            if(memberFileRepository.findByFileInfo(knowledgeEntity.getFileKey()).isEmpty()){
                knowledgeEntity.setFileKey(null);
            }
        }

        knowledgeEntity.setCategory(knowledgeUpdateDto.getCategory());
        knowledgeEntity.setTitle(knowledgeUpdateDto.getTitle());
        knowledgeEntity.setContent(knowledgeUpdateDto.getContent());
        knowledgeEntity.setModDate(LocalDateTime.now());
    }

    // 지식인 글 삭제
    public void deleteKnowledge(KnowledgeSendDto knowledgeSendDto, String userid) {
        Member member = memberRepository.findByEmailAndSocialYn(userid,knowledgeSendDto.getSocialYn()).orElseThrow(EntityNotFoundException::new);
        KnowledgeEntity knowledgeEntity = knowledgeRepository.findByUseridAndNum(member, knowledgeSendDto.getNum())
                                                            .orElseThrow(EntityNotFoundException::new);

        List<KnowledgeAnswerEntity> answerEntities = knowledgeAnswerRepository.findByKnNum(knowledgeEntity.getNum());

        // 답글 확인
        if(!answerEntities.isEmpty()) {
            knowledgeAnswerRepository.deleteAll(answerEntities);
        }

        // 첨부 파일 확인
        if(knowledgeEntity.getFileKey() != null) {
            List<MemberFile> memberFiles = memberFileRepository.findByFileInfo(knowledgeEntity.getFileKey());
            memberFileRepository.deleteAll(memberFiles);
        }

        knowledgeRepository.delete(knowledgeEntity);
    }


    // =========================================================
    // ========================== 답글 ==========================
    // =========================================================
    // 지식인 답글 리스트
    public List<KnowledgeAnswerDto> readAnswer(Long num) {
        List<KnowledgeAnswerEntity> resultEntity = knowledgeAnswerRepository.findByKnNumOrderByRegDateDesc(num);


        return resultEntity.stream().map(KnowledgeAnswerDto::toDto).collect(Collectors.toList());
    }

    // 지식인 답글 작성
    public List<KnowledgeAnswerDto> createAnswer(KnowledgeAnswerDto knowledgeAnswerDto, String userid) {
        KnowledgeAnswerEntity entity = KnowledgeAnswerDto.toEntity(knowledgeAnswerDto);
        entity.setAnswerWriter(userid);

        try {
            if ((entity.getAnswerWriter() == null) || entity.getAnswerWriter().equals("anonymousUser") ||
                    entity.getContent() == null || entity.getContent().replaceAll(" ","").isEmpty()) {
                return null;

            }
        } catch (NullPointerException e) {
            throw new RuntimeException("null 에러");
        }

        KnowledgeAnswerEntity save = knowledgeAnswerRepository.save(entity);
        List<KnowledgeAnswerEntity> byKnNum = knowledgeAnswerRepository.findByKnNumOrderByRegDateDesc(save.getKnNum());

        return byKnNum.stream().map(KnowledgeAnswerDto::toDto).collect(Collectors.toList());

    }

    // 지식인 답글 좋아요 1 증가
    public List<KnowledgeAnswerDto> likeAddAnswer(KnowledgeAnswerDto knowledgeAnswerDto) {
        KnowledgeAnswerEntity knowledgeAnswerEntity = knowledgeAnswerRepository.findByKnNumAndAnswerWriterAndContentAndModDate(
                knowledgeAnswerDto.getKnNum(),
                knowledgeAnswerDto.getAnswerWriter(),
                knowledgeAnswerDto.getContent(),
                knowledgeAnswerDto.getModDate()
        ).orElseThrow(EntityNotFoundException::new);

        int answerLike = knowledgeAnswerEntity.getAnswerLike();
        knowledgeAnswerEntity.setAnswerLike(answerLike+1);

        KnowledgeAnswerEntity save = knowledgeAnswerRepository.save(knowledgeAnswerEntity);

        List<KnowledgeAnswerEntity> resultEntity = knowledgeAnswerRepository.findByKnNumOrderByRegDateDesc(save.getKnNum());

        return resultEntity.stream().map(KnowledgeAnswerDto::toDto).collect(Collectors.toList());
    }

    // 지식인 답글 수정
    public List<KnowledgeAnswerDto> updateAnswer(KnowledgeAnswerDto knowledgeAnswerDto, String userid) {
        System.out.println("userid = " + userid);
        KnowledgeAnswerEntity knowledgeAnswerEntity = knowledgeAnswerRepository
                .findByIdAndAnswerWriter(knowledgeAnswerDto.getAnswerId(),knowledgeAnswerDto.getAnswerWriter())
                .orElseThrow(EntityNotFoundException::new);

        knowledgeAnswerEntity.setContent(knowledgeAnswerDto.getContent());
        knowledgeAnswerEntity.setModDate(LocalDateTime.now());

        System.out.println("knowledgeAnswerEntity = " + knowledgeAnswerEntity);
//        knowledgeAnswerRepository.save(knowledgeAnswerEntity);

        List<KnowledgeAnswerEntity> result = knowledgeAnswerRepository.findByKnNumOrderByRegDateDesc(knowledgeAnswerDto.getKnNum());

        return result.stream().map(KnowledgeAnswerDto::toDto).collect(Collectors.toList());
    }

    // 지식인 답글 삭제
    public List<KnowledgeAnswerDto> deleteAnswer(KnowledgeAnswerDto knowledgeAnswerDto, String userid) {
        System.out.println("userid = " + userid);
        KnowledgeAnswerEntity knowledgeAnswerEntity = knowledgeAnswerRepository.findByKnNumAndAnswerWriterAndContentAndModDate(
                knowledgeAnswerDto.getKnNum(),
                userid,
                knowledgeAnswerDto.getContent(),
                knowledgeAnswerDto.getModDate()
        ).orElseThrow(EntityNotFoundException::new);

        knowledgeAnswerRepository.delete(knowledgeAnswerEntity);

        List<KnowledgeAnswerEntity> result = knowledgeAnswerRepository.findByKnNumOrderByRegDateDesc(knowledgeAnswerDto.getKnNum());

        return result.stream().map(KnowledgeAnswerDto::toDto).collect(Collectors.toList());
    }

}
