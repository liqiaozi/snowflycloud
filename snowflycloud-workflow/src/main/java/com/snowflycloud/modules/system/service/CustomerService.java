package com.snowflycloud.modules.system.service;

import com.snowflycloud.common.bean.ResultResponse;

/**
 * @ClassName CustomerService
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/26 17:13
 * @Version 1.0
 **/

public interface CustomerService {
    ResultResponse queryCustomerConfig(String customrId);
}
