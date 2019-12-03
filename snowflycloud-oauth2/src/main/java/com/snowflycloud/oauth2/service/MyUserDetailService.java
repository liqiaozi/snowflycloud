package com.snowflycloud.oauth2.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ClassName MyUserDetailService
 * @Description TODO
 * @Author snowflying
 * @Date 2019/12/3 23:22
 * @Version 1.0
 **/
@Service(value = "userDetailsService")
public class MyUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
