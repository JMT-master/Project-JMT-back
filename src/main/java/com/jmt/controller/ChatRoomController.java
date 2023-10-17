package com.jmt.controller;

import com.jmt.repository.ChatRepository;
import com.jmt.service.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/chat")
public class ChatRoomController {

    //ChatRepository Bean 가져오기
    @Autowired
    private ChatRepository chatRepository;

    //채팅 리스트 화면 보여주기
    // /chat으로 요청이 들어오면 전체 채팅룸 리스트를 담아서 리턴하겠다

    //스프링 시큐리티를 통과한 유저 정보는 세션에 담김
    //principalDetail에 있는 chatUser 객체를 가져온다.
    @GetMapping
    public String goChatRoom(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){

        model.addAttribute("list", chatRepository.findAllRoom());

        //principalDetails 가 null이 아니라면 현재 상태는 로그인중
        if (principalDetails != null){
            //세션에서 로그인 한 유저 정보를 가져온다.
            model.addAttribute("user", principalDetails.getUser());
            log.info("user [{}]", principalDetails);
        }

        log.info("show all chatlist {}", chatRepository.findAllRoom());
        return "chat/roomlist";
    }
}
