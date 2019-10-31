package com.snowflycloud.modules.workflow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.snowflycloud.common.bean.PageResult;
import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.instance.BusinessObj;
import com.snowflycloud.modules.workflow.dto.instance.InstanceSearchDto;
import com.snowflycloud.modules.workflow.dto.instance.SnowProcessInstanceDto;
import com.snowflycloud.modules.workflow.dto.instance.StartProcessInstanceDto;
import com.snowflycloud.modules.workflow.service.SnowInstanceService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SnowInstanceServiceImpl
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/31 10:42
 * @Version 1.0
 **/
@Service
public class SnowInstanceServiceImpl implements SnowInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(SnowInstanceServiceImpl.class);


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;

    @Override
    public ResultResponse startProcessInstance(StartProcessInstanceDto startProcessInstanceDto) {
        logger.info("[startProcessInstance] 启动流程，params = {}", JSONObject.toJSONString(startProcessInstanceDto));

        String processDefinitionKey = startProcessInstanceDto.getProcessDefinitionKey();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).orderByDeploymentId().desc().singleResult();

        String processDefinitionId = processDefinition.getId();
        String businessKey = startProcessInstanceDto.getBusinessKey();
        Map<String, Object> processVariables = startProcessInstanceDto.getProcessVariables();

        BusinessObj businessObj = new BusinessObj();
        BeanUtils.copyProperties(startProcessInstanceDto, businessObj);
        if (processVariables == null) {
            processVariables = new HashMap<>();
        }
        processVariables.put("businessObj", businessObj);

        ProcessInstance instance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey, processVariables);
        logger.info("[startProcessInstance] 启动流程成功，instanceId = {}", instance.getId());
        return ResultResponse.ok(instance.getId());
    }

    @Override
    public ResultResponse queryRunningProcessInstance(InstanceSearchDto instanceSearchDto, Integer pageNumber, Integer pageSize) {
        logger.info("[queryRunningProcessInstance] 查询运行中的流程. instanceSearchDto = {},pageNumber = {},pageSize = {}",
            JSONObject.toJSONString(instanceSearchDto), pageNumber, pageSize);

        String processInstanceId = instanceSearchDto.getProcessInstanceId();
        String processInstanceName = instanceSearchDto.getProcessInstanceName();
        String processDefinitionKey = instanceSearchDto.getProcessDefinitionKey();
        String category = instanceSearchDto.getCategory();
        String status = instanceSearchDto.getStatus();

        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        if (StringUtils.isNotBlank(processInstanceId)) {
            processInstanceQuery.processInstanceId(processInstanceId);
        }
        if (StringUtils.isNotBlank(processInstanceName)) {
            processInstanceQuery.processInstanceNameLike(processInstanceName);
        }
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            processInstanceQuery.processDefinitionKey(processDefinitionKey);
        }
        if (StringUtils.isNotBlank(category)) {
            processInstanceQuery.processDefinitionCategory(category);
        }
        if (StringUtils.isNotBlank(status)) {
            if (status.equalsIgnoreCase("active")) {
                processInstanceQuery.active();
            } else if (status.equalsIgnoreCase("suspended")) {
                processInstanceQuery.suspended();
            }
        }

        List<ProcessInstance> processInstanceList = processInstanceQuery.listPage(pageSize * (pageNumber - 1), pageSize);
        List<SnowProcessInstanceDto> snowProcessInstanceDtoList = new ArrayList<>(processInstanceList.size());
        if (!CollectionUtils.isEmpty(processInstanceList)) {
            for (ProcessInstance processInstance : processInstanceList) {
                SnowProcessInstanceDto snowProcessInstanceDto = new SnowProcessInstanceDto();

                snowProcessInstanceDto.setProcessInstanceId(processInstance.getProcessInstanceId());
                snowProcessInstanceDto.setProcessDefinitionId(processInstance.getProcessDefinitionId());
                snowProcessInstanceDto.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
                snowProcessInstanceDto.setProcessDefinitionName(processInstance.getProcessDefinitionName());
                snowProcessInstanceDto.setProcessDefinitionVersion(processInstance.getProcessDefinitionVersion());
                snowProcessInstanceDto.setDeploymentId(processInstance.getDeploymentId());
                snowProcessInstanceDto.setActivityId(processInstance.getActivityId());
                snowProcessInstanceDto.setBusinessKey(processInstance.getBusinessKey());
                snowProcessInstanceDto.setTenantId(processInstance.getTenantId());

                HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
                snowProcessInstanceDto.setCreateTime(historicProcessInstance.getStartTime());

                Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).taskDefinitionKey(processInstance.getActivityId()).singleResult();
                snowProcessInstanceDto.setCurrentTaskName(task.getName());
                snowProcessInstanceDto.setCurrentAssignee(task.getAssignee());

                BusinessObj businessObj = (BusinessObj) runtimeService.getVariable(processInstance.getProcessInstanceId(),
                    "businessObj");
                if(null != businessObj){
                    snowProcessInstanceDto.setApplicant(businessObj.getApplicant());
                    snowProcessInstanceDto.setTitle(businessObj.getTitle());
                }

                snowProcessInstanceDtoList.add(snowProcessInstanceDto);
            }

        }

        long count = processInstanceQuery.count();
        PageResult<SnowProcessInstanceDto> pageResult = new PageResult<>(pageNumber, pageSize, count);
        pageResult.setList(snowProcessInstanceDtoList);
        return ResultResponse.ok(pageResult);
    }


}
