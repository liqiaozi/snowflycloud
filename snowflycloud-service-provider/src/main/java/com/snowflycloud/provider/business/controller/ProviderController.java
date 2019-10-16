package com.snowflycloud.provider.business.controller;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.provider.business.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ProviderController
 * @Description TODO
 * @Author 25823
 * @Date 2019/10/13 15:52
 * @Version 1.0
 **/
@Api(description = "用户服务管理")
@RestController
@RequestMapping("/provider/v1")
public class ProviderController {


    @ApiOperation(value = "hello方法")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "姓名", paramType = "query", required = true),
            @ApiImplicitParam(name = "age", value = "年龄", paramType = "query", required = true)})
    @GetMapping(value = "/hello")
    public ResultResponse hello(@RequestParam String name, @RequestParam String age) {

        String result = "hello " + name + ", welcome to my world!";
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return ResultResponse.ok(user);
    }

}
