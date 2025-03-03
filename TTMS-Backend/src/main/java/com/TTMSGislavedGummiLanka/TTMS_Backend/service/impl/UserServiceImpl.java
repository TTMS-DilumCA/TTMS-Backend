package com.TTMSGislavedGummiLanka.TTMS_Backend.service.impl;

import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.UserRepo;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepo.findByEmail(username)
                        .orElseThrow(()-> new UsernameNotFoundException("User not found"));
            }
        };
    }



}
