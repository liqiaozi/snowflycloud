package com.snowflycloud.modules.workflow.service;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.instance.InstanceSearchDto;
import com.snowflycloud.modules.workflow.dto.instance.StartProcessInstanceDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName SnowInstanceService
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/31 10:14
 * @Version 1.0
 **/

public interface SnowInstanceService {
    /**
     * 启动流程
     *
     * @param startProcessInstanceDto
     * @return
     */
    ResultResponse startProcessInstance(StartProcessInstanceDto startProcessInstanceDto);

    /**
     * 分页查询运行中的流程
     *
     * @param instanceSearchDto
     * @param pageNumber
     * @param pageSize
     * @return
     */
    ResultResponse queryRunningProcessInstance(InstanceSearchDto instanceSearchDto, Integer pageNumber, Integer pageSize);

    /**
     * 查询已经结束的流程.
     *
     * @param instanceSearchDto
     * @param pageNumber
     * @param pageSize
     * @return
     */
    ResultResponse queryFinishedProcessInstance(InstanceSearchDto instanceSearchDto, Integer pageNumber, Integer pageSize);

    /**
     * 挂起、激活流程实例
     *
     * @param processInstanceId
     * @param status
     * @return
     */
    ResultResponse updateProcessInstanceStatus(String processInstanceId, String status);

    /**
     * @param processInstanceIdList
     * @param deleteReason
     * @param status
     * @return
     */
    ResultResponse deleteProcessInstance(List<String> processInstanceIdList, String deleteReason, String status);

    /**
     * 显示流程图，带流程跟踪.
     *
     * @param processInstanceId
     * @param httpServletResponse
     */
    void showDiagram(String processInstanceId, HttpServletResponse httpServletResponse);
}
