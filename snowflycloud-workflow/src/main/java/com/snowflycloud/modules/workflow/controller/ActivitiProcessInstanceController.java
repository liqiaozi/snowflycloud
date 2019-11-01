package com.snowflycloud.modules.workflow.controller;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.instance.InstanceSearchDto;
import com.snowflycloud.modules.workflow.dto.instance.StartProcessInstanceDto;
import com.snowflycloud.modules.workflow.service.SnowInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
     * 审批详情
     * - 流程流转详情
     * - 审批记录
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

    /**
     * 查询已经结束的流程
     *
     * @param instanceSearchDto
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @PostMapping(value = "/finished")
    public ResultResponse finishedProcessInstance(@RequestBody InstanceSearchDto instanceSearchDto, @RequestParam(required = false, defaultValue = "1000") Integer pageSize,
                                                  @RequestParam(required = false, defaultValue = "1") Integer pageNumber) {
        return snowInstanceService.queryFinishedProcessInstance(instanceSearchDto, pageNumber, pageSize);
    }

    /**
     * 挂起/激活运行中的流程.
     *
     * @param processInstanceId
     * @param status
     * @return
     */
    @PostMapping(value = "/updateStatus")
    public ResultResponse updateProcessInstanceStatus(@RequestParam String processInstanceId, String status) {
        return snowInstanceService.updateProcessInstanceStatus(processInstanceId, status);
    }

    /**
     * 删除流程实例
     *
     * @param processInstanceIdList
     * @param status                running finished
     * @return
     */
    @DeleteMapping(value = "/delete")
    public ResultResponse deleteProcessInstance(@RequestBody List<String> processInstanceIdList, String deleteReason,
                                                @RequestParam String status) {
        return snowInstanceService.deleteProcessInstance(processInstanceIdList, deleteReason, status);
    }


    /**
     * 获取流程跟踪图.
     *
     * @param processInstanceId
     * @param httpServletResponse
     */
    @GetMapping(value = "/showDiagram/{processInstanceId}")
    public void showDiagram(@PathVariable String processInstanceId, HttpServletResponse httpServletResponse) {
        snowInstanceService.showDiagram(processInstanceId, httpServletResponse);
    }


}
