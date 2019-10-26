package com.snowflycloud.modules.system.controller;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.system.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CustomerController
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/26 16:56
 * @Version 1.0
 **/

@RequestMapping(value = "/system/customer/v1")
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    /**
     * 查询项目环境模板列表
     *
     * @return
     */
    @GetMapping(value = "/config/{customrId}")
    public ResultResponse queryCustomerConfig(@PathVariable String customrId) {
        ResultResponse result = customerService.queryCustomerConfig(customrId);
        return result;
    }

}
