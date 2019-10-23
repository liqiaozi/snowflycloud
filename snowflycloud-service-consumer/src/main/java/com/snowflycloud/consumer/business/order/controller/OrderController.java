package com.snowflycloud.consumer.business.order.controller;

import com.google.common.collect.Lists;
import com.snowflycloud.common.bean.ResultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName OrderController
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/23 13:02
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/order/v1")
public class OrderController {

    @GetMapping(value = "/list")
    public ResultResponse getOrderListByUser(@RequestParam String username){

        List<String> orderList = Lists.newArrayList();
        orderList.add("姓名：" +  username + "订单号：NO_001");
        orderList.add("姓名：" +  username + "订单号：NO_002");
        return ResultResponse.ok(orderList);
    }

}
