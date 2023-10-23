package com.jmt.common;

public class ExpiredTime {
    //기본 만료 시간, 토큰과 알람을 위한 SseEmitter의 만료 시간 통일을 위해 사용
    static final public Long EXPIRED_TIMEOUT = 1L*(1000*60*60);
}
