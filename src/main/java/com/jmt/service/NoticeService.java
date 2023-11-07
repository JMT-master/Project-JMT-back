package com.jmt.service;

import com.jmt.constant.Board;
import com.jmt.dto.KnowledgeDto;
import com.jmt.dto.NoticeDto;
import com.jmt.dto.NoticeSendDto;
import com.jmt.entity.Member;
import com.jmt.entity.MemberFile;
import com.jmt.entity.Notice;
import com.jmt.repository.MemberFileRepository;
import com.jmt.repository.MemberRepository;
import com.jmt.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository repository;
    private final MemberRepository memberRepository;
    private final MemberFileRepository memberFileRepository;

    private final FileService fileService;

    @Transactional
    public NoticeDto createNotice(List<MultipartFile> multipartFiles, NoticeDto noticeDto, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Notice notice = NoticeDto.toEntity(noticeDto);
        notice.setMember(member);
        Long num = 0L;
        Optional<Long> l = repository.getNoticeByMaxIdx();
        if(l.isPresent()) {
            num = l.get();
        }

        num += 1;
        notice.setNoticeIdx(num); // 글번호 Entity에 등록

        if(multipartFiles != null) {
            String fileKey = fileService.fileUpload(multipartFiles, email, Board.NOTICE, num.intValue());
            notice.setNoticeFileKey(fileKey);
        }

        repository.save(notice);
        return NoticeDto.toDto(notice);
    }

    @Transactional
    public List<NoticeSendDto> readNoticeIdx(Long idx){
        Notice notice = repository.findByNoticeIdx(idx);

        List<NoticeSendDto> noticeSendDtos = new ArrayList<>();

        notice.setNoticeView(notice.getNoticeView()+1);
        if(notice.getNoticeFileKey() != null){
            List<MemberFile> memberFiles = memberFileRepository.findByFileInfo(notice.getNoticeFileKey());
            memberFiles.forEach(memberFile -> {
                NoticeSendDto dto = NoticeSendDto.toDto(notice);
                dto.setServerPath(memberFile.getFileServerPath());
                dto.setOriginalName(memberFile.getFileName());
                dto.setView(notice.getNoticeView());
                noticeSendDtos.add(dto);
            });
        }else{
            NoticeSendDto dto = NoticeSendDto.toDto(notice);
            dto.setView(notice.getNoticeView());
            noticeSendDtos.add(dto);
        }
        repository.save(notice);
        return noticeSendDtos;
    }

    @Transactional
    public Page<NoticeDto> search(String select, String result, Pageable pageable){
        Page<Notice> searchResult = null;
        switch (select){
            case "title" : {
                searchResult = repository.findByNoticeTitleContaining(result, pageable);
                break;
            }
            case "content" : {
                searchResult = repository.findByNoticeContentContaining(result, pageable);
            }
        }
        return Objects.requireNonNull(searchResult).map(NoticeDto::toDto);
    }

    @Transactional
    public Page<NoticeDto> readAllNotice(Pageable pageable){
        Page<Notice> notices = repository.findAllByOrderByNoticeIdxDesc(pageable);

        return notices.map(NoticeDto::toDto);
    }

    @Transactional
    public Notice updateNotice(NoticeDto dto){
        Notice notice = repository.findByNoticeIdx(dto.getIdx());

        if(!notice.getNoticeFileKey().isEmpty()) {
            List<MemberFile> memberFiles = memberFileRepository.findByFileInfo(notice.getNoticeFileKey());

            List<MemberFile> deleteFiles = memberFiles.stream().filter(data -> !dto.getFiles().contains(data.getFileName()))
                    .collect(Collectors.toList());

            memberFileRepository.deleteAll(deleteFiles);
        }

        notice.setNoticeCategory(dto.getCategory());
        notice.setNoticeTitle(dto.getTitle());
        notice.setNoticeContent(dto.getContent());
        notice.setRegDate(LocalDateTime.now());
        return notice;
    }

    @Transactional
    public void deleteNotice(NoticeSendDto noticeSendDto){
        Notice notice = repository.findByNoticeIdx(noticeSendDto.getIdx());
        if(notice != null){
            List<MemberFile> memberFiles = memberFileRepository.findByFileInfo(notice.getNoticeFileKey());
            memberFileRepository.deleteAll(memberFiles);
        }
        repository.delete(notice);
    }


}
