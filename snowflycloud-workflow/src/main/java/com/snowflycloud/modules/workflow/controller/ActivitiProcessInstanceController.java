package com.snowflycloud.modules.workflow.controller;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.instance.InstanceSearchDto;
import com.snowflycloud.modules.workflow.dto.instance.StartProcessInstanceDto;
import com.snowflycloud.modules.workflow.service.SnowInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ActivitiProcessInstanceController
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/30 20:52
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/instance/v1")
public class ActivitiProcessInstanceController {

    @Autowired
    private SnowInstanceService snowInstanceService;

    /**
     * 启动流程.
     *
     * @param startProcessInstanceDto
     * @return
     */
    @PostMapping(value = "/start")
    public ResultResponse startProcessInstance(@RequestBody StartProcessInstanceDto startProcessInstanceDto) {
        return snowInstanceService.startProcessInstance(startProcessInstanceDto);
    }


    /**
     * 获取结束的流程实例
     * 激活、挂起流程
     * 审批详情
     * - 流程图带踪迹
     * - 流程流转详情
     * - 审批记录
     * 批量删除运行中的流程
     * 批量删除运行中的实例
     * 获取当前环节的审批人
     */

    /**
     * 查询运行中的流程
     *
     * @param instanceSearchDto
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @PostMapping(value = "/running")
    public ResultResponse runningProcessInstance(@RequestBody InstanceSearchDto instanceSearchDto, @RequestParam(required = false, defaultValue = "1000") Integer pageSize,
                                                 @RequestParam(required = false, defaultValue = "1") Integer pageNumber) {
        return snowInstanceService.queryRunningProcessInstance(instanceSearchDto, pageNumber, pageSize);
    }


}
