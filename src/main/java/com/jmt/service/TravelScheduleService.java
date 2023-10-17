package com.jmt.service;

import com.jmt.repository.DayFormatRepository;
import com.jmt.repository.TravelScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelScheduleService {

    private final TravelScheduleRepository travelScheduleRepository;

    private final DayFormatRepository dayFormatRepository;


}
