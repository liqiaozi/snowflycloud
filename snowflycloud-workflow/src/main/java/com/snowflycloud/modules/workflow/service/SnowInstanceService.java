package com.snowflycloud.modules.workflow.service;

import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.instance.InstanceSearchDto;
import com.snowflycloud.modules.workflow.dto.instance.StartProcessInstanceDto;

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
}
