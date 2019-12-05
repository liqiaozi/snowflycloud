package com.snowflycloud.oauth2.feignclient;


import com.snowflycloud.common.bean.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-center")
public interface UserFeignClient {

    @GetMapping(value = "/user/v1/queryUserByUsername/{username}")
    ResultResponse queryUserByUsername(@PathVariable String username);
}
