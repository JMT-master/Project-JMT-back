package com.jmt.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {
    //이미터는 DB가 따로 필요하지 않기 때문에 JPA를 이용한 인터페이스가 아닌 클래스로 작성

    //모든 이미터를 저장하는 ConcurrentHashMap
    // ConcurrentHashMap은 읽기 작업에는 여러 쓰레드가 동시에 읽을 수 있지만,
    // 쓰기 작업에는 특정 세그먼트 or 버킷에 대한 Lock을 사용
    private final Map<String, SseEmitter> emitters =new ConcurrentHashMap<>();

//    주어진 아이디, 이미터를 저장
//    userid - 사용자의 아이디
//    emitter - 이벤트 emitter
    public void save(String userid, SseEmitter emitter){
        System.out.println("save의 이미터 : " + userid + "의 이미터 : " + emitter);
        emitters.put(userid, emitter);
    }

    public Map<String,SseEmitter> findAllEmitterByMemberId(String userid){
        System.out.println("emitters = " + emitters);
        return emitters.entrySet().stream()
                //emitter 저장시 맵<유저 아이디, 이미터> 타입으로 저장
                //등록된 emiiters중에 유저 아이디가 userid와 똑같이 시작하거나 끝나는 이미터를 제외하고 필터
                .filter(entry -> entry.getKey().startsWith(userid))
                //필터링 된 이미터에서 키와 밸류를 가져와 맵으로 만들어 반환
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

//    해당되는 id 이미터를 제거
    public void deleteById(String userid){
        emitters.remove(userid);
    }

    public SseEmitter get(String userid){
        return emitters.get(userid);
    }
}
