package com.snowflycloud.oauth2.controller;

import com.snowflycloud.common.bean.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

/**
 *
 **/
@RestController
@RequestMapping("/api/oauth2/v1")
public class OAuth2Controller {
    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping(value = "/user")
    public Authentication principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    /**
     * 退出登录
     * @param access_token
     * @return
     */
    @PostMapping(value = "/exit")
    public ResultResponse revokeToken(@RequestParam String access_token) {
        boolean revokeToken = consumerTokenServices.revokeToken(access_token);
        if (revokeToken) {
            return ResultResponse.ok();
        } else {
            return ResultResponse.error("注销失败");
        }
    }


}
