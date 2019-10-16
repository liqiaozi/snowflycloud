package com.snowflycloud.consumer.business.controller;

import com.google.common.collect.Lists;
import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.consumer.business.domain.Order;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ConsumerController
 * @Description TODO
 * @Author 25823
 * @Date 2019/10/13 16:07
 * @Version 1.0
 **/
@Api(description = "订单服务管理")
@RestController
@RequestMapping(value = "/consumer/v1")
public class ConsumerController {

    @GetMapping(value = "/orders")
    public ResultResponse queryOrders(@RequestParam String name){

        List<Order> orderList = Lists.newArrayList();
        Order order1 = new Order().builder().name(name).orderId("001").orderName("订单001").build();
        Order order2 = new Order().builder().name(name).orderId("002").orderName("订单002").build();
        orderList.add(order1);
        orderList.add(order2);
        return ResultResponse.ok(orderList);
    }
}
