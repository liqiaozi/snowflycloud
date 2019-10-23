package com.snowflycloud.provider.business.user.controller;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.provider.feignclient.OrderFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/23 13:16
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/user/v1")
public class UserController {

    @Autowired
    private OrderFeignClient orderFeignClient;

    @GetMapping(value = "/orderList")
    public ResultResponse getorderList(@RequestParam String username){
        ResultResponse response = orderFeignClient.getOrderListByUser(username);
        return response;
    }


}
