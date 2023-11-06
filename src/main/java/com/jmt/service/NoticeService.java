package com.jmt.service;

import com.jmt.constant.Board;
import com.jmt.dto.KnowledgeDto;
import com.jmt.dto.NoticeDto;
import com.jmt.entity.Member;
import com.jmt.entity.Notice;
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
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository repository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    @Transactional
    public void createNotice(List<MultipartFile> multipartFiles, NoticeDto noticeDto, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Notice notice = NoticeDto.toEntity(noticeDto);
        notice.setMember(member);
        Long num = 0L;
        Optional<Long> l = repository.getNoticeByMaxIdx();
        if(l.isPresent())  num = l.get();

        num += 1;
        notice.setNoticeIdx(num); // 글번호 Entity에 등록

        if(multipartFiles != null) {
            String fileKey = fileService.fileUpload(multipartFiles, email, Board.NOTICE, num.intValue());
            notice.setNoticeFileKey(fileKey);
        }

        repository.save(notice);
    }

    @Transactional
    public Notice readNoticeIdx(Long idx){
        return repository.findByNoticeIdx(idx);
    }

    @Transactional
    public Notice readNotice(String noticeId){
        return repository.findById(noticeId).get();
    }

    @Transactional
    public Page<NoticeDto> readAllNotice(Pageable pageable){
        Page<Notice> notices = repository.findAllByOrderByNoticeIdxDesc(pageable);

        return notices.map(NoticeDto::toDto);
    }

    @Transactional
    public Notice updateNotice(NoticeDto dto){
        Notice notice = repository.findByNoticeIdx(dto.getIdx());
        notice.setNoticeCategory(dto.getCategory());
        notice.setNoticeTitle(dto.getTitle());
        notice.setNoticeContent(dto.getContent());
        notice.setRegDate(LocalDateTime.now());
        repository.save(notice);
        return notice;
    }

    @Transactional
    public void deleteNotice(Notice notice){
        repository.delete(notice);
    }


}
