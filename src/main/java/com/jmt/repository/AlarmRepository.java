package com.jmt.repository;

import com.jmt.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, String> {
    public List<Alarm> findAlarmsByMember_Userid(String userid);

    public Alarm findByAlarmId(String alarmId);
}
