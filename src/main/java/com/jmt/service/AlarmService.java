package com.jmt.service;


import com.jmt.dto.AlarmDto;
import com.jmt.entity.Alarm;
import com.jmt.entity.Member;
import com.jmt.repository.AlarmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AlarmService {

    @Autowired
    AlarmRepository repository;

    @Transactional
    public void addAlarm(Alarm entity){
        repository.save(entity);
    }

    @Transactional
    public List<Alarm> showAlarm(Member member){
        log.info("AlarmTest :" + repository.findAlarmsByMember_Userid(member.getEmail()));
        return repository.findAlarmsByMember_Userid(member.getEmail());
    }

    @Transactional
    public Alarm getAlarm(String alarmId){
        return repository.findByAlarmId(alarmId);
    }

    @Transactional
    public void deleteAlarm(String alarmId){
        repository.delete(getAlarm(alarmId));
    }
}
