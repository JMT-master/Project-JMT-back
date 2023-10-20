package com.jmt.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {

    //모든 이미터를 저장하는 ConcurrentHashMap
    // ConcurrentHashMap은 읽기 작업에는 여러 쓰레드가 동시에 읽을 수 있지만,
    // 쓰기 작업에는 특정 세그먼트 or 버킷에 대한 Lock을 사용
    private final Map<String, SseEmitter> emitters =new ConcurrentHashMap<>();

//    주어진 아이디, 이미터를 저장
//    userid - 사용자의 아이디
//    emitter - 이벤트 emitter
    public void save(String userid, SseEmitter emitter){
        emitters.put(userid, emitter);
    }

//    해당되는 id 이미터를 제거
    public void deleteById(String userid){
        emitters.remove(userid);
    }

    public SseEmitter get(String userid){
        return emitters.get(userid);
    }
}
