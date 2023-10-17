package com.jmt.chat.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Controller
public class WebSocketHandler extends TextWebSocketHandler implements InitializingBean {

    //websocket 연결 성공 했을 때

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
