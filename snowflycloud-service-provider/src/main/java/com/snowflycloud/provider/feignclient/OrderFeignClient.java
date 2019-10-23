package com.snowflycloud.provider.feignclient;

import com.snowflycloud.common.bean.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName OrderFeignClient
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/23 13:13
 * @Version 1.0
 **/
@FeignClient(value = "snowfly-service-consumer")
public interface OrderFeignClient {

    /**
     * 获取订单信息
     * @param username
     * @return
     */
    @GetMapping(value = "/order/v1/list")
    ResultResponse getOrderListByUser(@RequestParam String username);

}
