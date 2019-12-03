package com.snowflycloud.usercenter.modules.user.service.impl;

import com.snowflycloud.usercenter.modules.user.dao.UserDao;
import com.snowflycloud.usercenter.modules.user.domain.User;
import com.snowflycloud.usercenter.modules.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 **/
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User queryUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);

        return user;
    }

    @Override
    public User queryUserByMobile(String mobile) {
        User user = userDao.findUserByMobile(mobile);
        return user;
    }

    @Override
    public User queryUserByEmail(String email) {
        User user = userDao.findUserByEmail(email);
        return user;
    }
}
