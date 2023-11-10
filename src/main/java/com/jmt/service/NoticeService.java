package com.jmt.service;

import com.jmt.constant.Board;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Member member = memberRepository.findByEmailAndSocialYn(email, noticeDto.getSocialYn()).orElseThrow(EntityNotFoundException::new);
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
        log.debug("readNotice : " + notice);
        List<NoticeSendDto> noticeSendDtos = new ArrayList<>();

        notice.setNoticeView(notice.getNoticeView()+1);
        if(notice.getNoticeFileKey() != null){
            log.debug("111");
            List<MemberFile> memberFiles = memberFileRepository.findByFileInfo(notice.getNoticeFileKey());
            memberFiles.forEach(memberFile -> {
                NoticeSendDto dto = NoticeSendDto.toDto(notice);
                dto.setServerPath(memberFile.getFileServerPath());
                dto.setOriginalName(memberFile.getFileName());
                dto.setView(notice.getNoticeView());
                noticeSendDtos.add(dto);
            });
        }else{
            log.debug("222");
            NoticeSendDto dto = NoticeSendDto.toDto(notice);
            dto.setView(notice.getNoticeView());
            noticeSendDtos.add(dto);
        }
        log.debug("noticeseverce : " + noticeSendDtos);
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
        log.debug("update notcie : " + notice);
        log.debug("update dto : " + dto);
        log.debug("update null : " + notice.getNoticeFileKey());
        if(notice.getNoticeFileKey() != null) {
            log.debug("null filekey");
            List<MemberFile> memberFiles = memberFileRepository.findByFileInfo(notice.getNoticeFileKey());

            List<MemberFile> deleteFiles = memberFiles.stream().filter(data -> !dto.getFiles().contains(data.getFileName()))
                    .collect(Collectors.toList());

            deleteFiles.forEach(deleteFile -> {
                Path filePath = Paths.get(deleteFile.getFileServerPath());
                try {
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            memberFileRepository.deleteAll(deleteFiles);
            if(memberFileRepository.findByFileInfo(notice.getNoticeFileKey()).isEmpty()){
                notice.setNoticeFileKey(null);
            }

        }

        notice.setNoticeCategory(dto.getCategory());
        notice.setNoticeTitle(dto.getTitle());
        notice.setNoticeContent(dto.getContent());
        notice.setRegDate(LocalDateTime.now());
        log.debug("update notcie2 : " + notice);
        repository.save(notice);
        return notice;
    }

    @Transactional
    public void deleteNotice(NoticeSendDto noticeSendDto){
        Notice notice = repository.findByNoticeIdx(noticeSendDto.getIdx());
        if(notice != null){
            List<MemberFile> memberFiles = memberFileRepository.findByFileInfo(notice.getNoticeFileKey());
            memberFiles.forEach(memberFile -> {
                Path filePath = Paths.get(memberFile.getFileServerPath());
                try {
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            memberFileRepository.deleteAll(memberFiles);
        }
        repository.delete(notice);
    }

    @Transactional
    public List<NoticeDto> mainNoticeList(){

        List<Notice> noticeList = repository.findTop2ByOrderByRegDateDesc();

        if (noticeList.isEmpty()){
            List<NoticeDto> emptyDtos = new ArrayList<>();
            return emptyDtos;
        }
        return noticeList.stream()
                .map(NoticeDto::toDto)
                .collect(Collectors.toList());
    }
}
