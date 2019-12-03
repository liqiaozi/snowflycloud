package com.snowflycloud.usercenter.modules.user.controller;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.usercenter.modules.user.domain.User;
import com.snowflycloud.usercenter.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户服务管理
 **/
@ResponseBody
@RequestMapping("/user/v1")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名查询用户.
     *
     * @param username
     *
     * @return
     */
    @GetMapping(value = "/queryUserByUsername/{username}")
    public ResultResponse queryUserByUsername(@PathVariable String username) {
        User user = userService.queryUserByUsername(username);
        return ResultResponse.ok(user);
    }
}
