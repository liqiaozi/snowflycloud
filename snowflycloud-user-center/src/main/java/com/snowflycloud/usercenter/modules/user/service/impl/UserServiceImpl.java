package com.snowflycloud.usercenter.modules.user.service.impl;

import com.snowflycloud.usercenter.modules.user.domain.User;
import com.snowflycloud.usercenter.modules.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 **/
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User queryUserByUsername(String username) {

        return null;
    }

    @Override
    public User queryUserByMobile(String mobile) {
        return null;
    }

    @Override
    public User queryUserByEmail(String email) {
        return null;
    }
}
