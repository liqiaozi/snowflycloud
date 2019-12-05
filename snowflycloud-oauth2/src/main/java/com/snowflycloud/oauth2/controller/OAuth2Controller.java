package com.snowflycloud.oauth2.controller;

import com.snowflycloud.common.bean.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 **/
@RestController
@RequestMapping("/api/oauth2/v1")
public class OAuth2Controller {
    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping(value = "/user")
    public Authentication principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @PostMapping(value = "/exit/{access_token}")
    public ResultResponse revokeToken(String access_token) {
        boolean revokeToken = consumerTokenServices.revokeToken(access_token);
        if (revokeToken) {
            return ResultResponse.ok();
        } else {
            return ResultResponse.error("注销失败");
        }
    }


}
