package com.snowflycloud.usercenter.modules.user.controller;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.common.dto.user.UserDto;
import com.snowflycloud.usercenter.modules.user.domain.User;
import com.snowflycloud.usercenter.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务管理
 **/
@RestController
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
    @GetMapping(value = "/queryUserByUsername")
    public UserDto queryUserByUsername(@RequestParam(value = "username") String username) {
        UserDto userDto = userService.queryUserByUsername(username);

        return userDto;
    }

    /**
     * 查询用户列表
     *
     * @return
     */
    @GetMapping(value = "/list")
    public ResultResponse queryUserList() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setUsername("aa");
        User user2 = new User();
        user2.setUsername("aa");
        userList.add(user1);
        userList.add(user2);
        return ResultResponse.ok(userList);
    }
}
