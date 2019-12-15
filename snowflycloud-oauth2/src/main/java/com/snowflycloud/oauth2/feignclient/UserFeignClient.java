package com.snowflycloud.oauth2.feignclient;


import com.snowflycloud.common.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "snowfly-user-center")
public interface UserFeignClient {

    @GetMapping(value = "/user/v1/queryUserByUsername")
    UserDto queryUserByUsername(@RequestParam(value = "username") String username);
}
