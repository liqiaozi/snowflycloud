package com.snowflycloud.usercenter.modules.user.service;

import com.snowflycloud.usercenter.modules.user.domain.User;

/**
 * 用户服务接口
 **/
public interface UserService {

    /**
     * 通过用户名查询用户
     *
     * @param username
     *
     * @return
     */
    User queryUserByUsername(String username);

    /**
     * 通过手机号查询用户
     *
     * @param mobile
     *
     * @return
     */
    User queryUserByMobile(String mobile);

    /**
     * 通过邮箱查询用户
     *
     * @param email
     *
     * @return
     */
    User queryUserByEmail(String email);


}
