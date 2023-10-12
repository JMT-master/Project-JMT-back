package com.jmt.chat.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Controller
public class WebSocketHandler extends TextWebSocketHandler implements InitializingBean {


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
