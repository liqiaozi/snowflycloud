package com.snowflycloud.oauth2.service;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.common.constant.UserConstant;
import com.snowflycloud.common.dto.user.UserDto;
import com.snowflycloud.oauth2.feignclient.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 **/
@Service(value = "userDetailsService")
public class MyUserDetailService implements UserDetailsService {


    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResultResponse response = userFeignClient.queryUserByUsername(username);

        if (response.getData() == null) {
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        }
        UserDto userDto = (UserDto) response.getData();
        if (UserConstant.USER_STATUS_DISABLED.equals(userDto.getStatus())) {
            throw new DisabledException("用户不可用");
        }

        return userDto;
    }
}
