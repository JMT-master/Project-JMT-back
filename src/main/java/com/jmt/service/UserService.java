package com.jmt.service;

import com.jmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);



    @Override
    public UserDetails loadUserByUsername(String userid){
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. userid : {}", userid);
        return userRepository.getByUserid(userid);
    }
}