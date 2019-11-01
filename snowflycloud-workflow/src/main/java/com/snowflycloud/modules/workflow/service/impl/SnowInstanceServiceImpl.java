package com.snowflycloud.modules.workflow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.snowflycloud.common.bean.PageResult;
import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.modules.workflow.dto.instance.*;
import com.snowflycloud.modules.workflow.service.SnowInstanceService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

    @Autowired
    private ProcessEngine processEngine;

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
        List<SnowRunningProcessInstanceDto> snowRunningProcessInstanceDtoList = new ArrayList<>(processInstanceList.size());
        if (!CollectionUtils.isEmpty(processInstanceList)) {
            for (ProcessInstance processInstance : processInstanceList) {
                SnowRunningProcessInstanceDto snowRunningProcessInstanceDto = new SnowRunningProcessInstanceDto();

                snowRunningProcessInstanceDto.setProcessInstanceId(processInstance.getProcessInstanceId());
                snowRunningProcessInstanceDto.setProcessDefinitionId(processInstance.getProcessDefinitionId());
                snowRunningProcessInstanceDto.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
                snowRunningProcessInstanceDto.setProcessDefinitionName(processInstance.getProcessDefinitionName());
                snowRunningProcessInstanceDto.setProcessDefinitionVersion(processInstance.getProcessDefinitionVersion());
                snowRunningProcessInstanceDto.setDeploymentId(processInstance.getDeploymentId());
                snowRunningProcessInstanceDto.setActivityId(processInstance.getActivityId());
                snowRunningProcessInstanceDto.setBusinessKey(processInstance.getBusinessKey());
                snowRunningProcessInstanceDto.setTenantId(processInstance.getTenantId());

                HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
                snowRunningProcessInstanceDto.setCreateTime(historicProcessInstance.getStartTime());

                Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).taskDefinitionKey(processInstance.getActivityId()).singleResult();
                snowRunningProcessInstanceDto.setCurrentTaskName(task.getName());
                snowRunningProcessInstanceDto.setCurrentAssignee(task.getAssignee());

                BusinessObj businessObj = (BusinessObj) runtimeService.getVariable(processInstance.getProcessInstanceId(),
                    "businessObj");
                if (null != businessObj) {
                    snowRunningProcessInstanceDto.setApplicant(businessObj.getApplicant());
                    snowRunningProcessInstanceDto.setTitle(businessObj.getTitle());
                }

                snowRunningProcessInstanceDtoList.add(snowRunningProcessInstanceDto);
            }

        }

        long count = processInstanceQuery.count();
        PageResult<SnowRunningProcessInstanceDto> pageResult = new PageResult<>(pageNumber, pageSize, count);
        pageResult.setList(snowRunningProcessInstanceDtoList);
        return ResultResponse.ok(pageResult);
    }

    @Override
    public ResultResponse queryFinishedProcessInstance(InstanceSearchDto instanceSearchDto, Integer pageNumber, Integer pageSize) {
        logger.info("[queryRunningProcessInstance] 查询已经结束的流程. instanceSearchDto = {},pageNumber = {},pageSize = {}",
            JSONObject.toJSONString(instanceSearchDto), pageNumber, pageSize);

        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        String processInstanceId = instanceSearchDto.getProcessInstanceId();
        String processInstanceName = instanceSearchDto.getProcessInstanceName();
        String processDefinitionKey = instanceSearchDto.getProcessDefinitionKey();
        String category = instanceSearchDto.getCategory();
        if (StringUtils.isNotBlank(processInstanceId)) {
            historicProcessInstanceQuery.processInstanceId(processInstanceId);
        }
        if (StringUtils.isNotBlank(processInstanceName)) {
            historicProcessInstanceQuery.processInstanceNameLike(processInstanceName);
        }
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            historicProcessInstanceQuery.processDefinitionKey(processDefinitionKey);
        }
        if (StringUtils.isNotBlank(category)) {
            historicProcessInstanceQuery.processDefinitionCategory(category);
        }


        List<HistoricProcessInstance> historicProcessInstanceList = historicProcessInstanceQuery.listPage(pageSize * (pageNumber - 1), pageSize);
        List<SnowFinishedProcessInstanceDto> snowFinishedProcessInstanceDtoList =
            new ArrayList<SnowFinishedProcessInstanceDto>(historicProcessInstanceList.size());
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstanceList) {
            SnowFinishedProcessInstanceDto snowFinishedProcessInstanceDto = new SnowFinishedProcessInstanceDto();
            snowFinishedProcessInstanceDto.setProcessInstanceId(historicProcessInstance.getId());
            snowFinishedProcessInstanceDto.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
            snowFinishedProcessInstanceDto.setProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
            snowFinishedProcessInstanceDto.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
            snowFinishedProcessInstanceDto.setProcessDefinitionVersion(historicProcessInstance.getProcessDefinitionVersion());
            snowFinishedProcessInstanceDto.setDeploymentId(historicProcessInstance.getDeploymentId());

            snowFinishedProcessInstanceDto.setBusinessKey(historicProcessInstance.getBusinessKey());
            snowFinishedProcessInstanceDto.setTenantId(historicProcessInstance.getTenantId());
            snowFinishedProcessInstanceDto.setCreateTime(historicProcessInstance.getStartTime());
            snowFinishedProcessInstanceDto.setEndTime(historicProcessInstance.getEndTime());
            snowFinishedProcessInstanceDto.setDuration(historicProcessInstance.getDurationInMillis());
            snowFinishedProcessInstanceDtoList.add(snowFinishedProcessInstanceDto);

            Map<String, Object> processVariables = historicProcessInstance.getProcessVariables();
            BusinessObj businessObj = (BusinessObj) processVariables.get("businessObj");

            if (null != businessObj) {
                snowFinishedProcessInstanceDto.setApplicant(businessObj.getApplicant());
                snowFinishedProcessInstanceDto.setTitle(businessObj.getTitle());
                snowFinishedProcessInstanceDto.setDetailAddress(businessObj.getDetailAddress());
            }

            snowFinishedProcessInstanceDto.setResult(null);
            snowFinishedProcessInstanceDto.setReasons(null);
        }
        long count = historicProcessInstanceQuery.count();

        PageResult<SnowFinishedProcessInstanceDto> pageResult = new PageResult<>(pageNumber, pageSize, count);
        pageResult.setList(snowFinishedProcessInstanceDtoList);
        return ResultResponse.ok(pageResult);
    }

    @Override
    public ResultResponse updateProcessInstanceStatus(String processInstanceId, String status) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (null != processInstance) {
            if ("suspended".equalsIgnoreCase(status)) {
                runtimeService.suspendProcessInstanceById(processInstanceId);
            } else if ("active".equalsIgnoreCase(status)) {
                runtimeService.activateProcessInstanceById(processInstanceId);
            }
        }
        return ResultResponse.ok();
    }

    @Override
    public ResultResponse deleteProcessInstance(List<String> processInstanceIdList, String deleteReason, String status) {
        if (!CollectionUtils.isEmpty(processInstanceIdList)) {
            if (StringUtils.isNotBlank(status) && "running".equalsIgnoreCase(status)) {
                for (String processInstanceId : processInstanceIdList) {
                    runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
                }
            }
        }

        return ResultResponse.ok();
    }

    @Override
    public void showDiagram(String processInstanceId, HttpServletResponse response) {

        try {
            byte[] processImage = this.getProcessImage(processInstanceId);

            OutputStream outputStream = response.getOutputStream();
            InputStream inputStream = new ByteArrayInputStream(processImage);
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            logger.error("[showDiagram] 生成流程跟踪图异常----{}", e.getStackTrace());
        }

    }

    /**
     * 获取流程图片.
     *
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public byte[] getProcessImage(String processInstanceId) throws Exception {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (null == historicProcessInstance) {
            throw new Exception("流程不存在");
        }

        // 获取流程定义
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(historicProcessInstance.getProcessDefinitionId());

        // 获取流程历史中已执行节点，并按照节点在流程中先后顺序排序
        List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().desc().list();

        int index = 1;
        // 已执行的节点ID集合
        List<String> executedActivityIdList = new ArrayList<>();
        logger.info("[getProcessImage] 获取已经执行的节点ID");
        for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
            if (index == 1) {
                executedActivityIdList.add(activityInstance.getActivityId() + "#");
            } else {
                executedActivityIdList.add(activityInstance.getActivityId());
            }
            index++;
        }

        // 获取流程图图像字符流
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

        //已执行flow的集和
        List<String> executedFlowIdList = getHighLightedFlows(bpmnModel, historicActivityInstanceList);


        ProcessDiagramGenerator processDiagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
        InputStream imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", executedActivityIdList,
            executedFlowIdList, "宋体", "微软雅黑", "黑体", null, 2.0);

        byte[] buffer = new byte[imageStream.available()];
        imageStream.read(buffer);
        imageStream.close();
        return buffer;

    }

    /**
     * 获取已经流转的线
     *
     * @param bpmnModel
     * @param historicActivityInstances
     * @return
     */
    private static List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 高亮流程已发生流转的线id集合
        List<String> highLightedFlowIds = new ArrayList<>();
        // 全部活动节点
        List<FlowNode> historicActivityNodes = new ArrayList<>();
        // 已完成的历史活动节点
        List<HistoricActivityInstance> finishedActivityInstances = new ArrayList<>();

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId());
            historicActivityNodes.add(flowNode);
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstances.add(historicActivityInstance);
            }
        }

        FlowNode currentFlowNode = null;
        FlowNode targetFlowNode = null;
        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
            // 获得当前活动对应的节点信息及outgoingFlows信息
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId());
            List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();

            /**
             * 遍历outgoingFlows并找到已已流转的 满足如下条件认为已已流转： 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
             */
            if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配流程目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef());
                    if (historicActivityNodes.contains(targetFlowNode)) {
                        highLightedFlowIds.add(targetFlowNode.getId());
                    }
                }
            } else {
                List<Map<String, Object>> tempMapList = new ArrayList<>();
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                        if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("highLightedFlowId", sequenceFlow.getId());
                            map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
                            tempMapList.add(map);
                        }
                    }
                }

                if (!CollectionUtils.isEmpty(tempMapList)) {
                    // 遍历匹配的集合，取得开始时间最早的一个
                    long earliestStamp = 0L;
                    String highLightedFlowId = null;
                    for (Map<String, Object> map : tempMapList) {
                        long highLightedFlowStartTime = Long.valueOf(map.get("highLightedFlowStartTime").toString());
                        if (earliestStamp == 0 || earliestStamp >= highLightedFlowStartTime) {
                            highLightedFlowId = map.get("highLightedFlowId").toString();
                            earliestStamp = highLightedFlowStartTime;
                        }
                    }

                    highLightedFlowIds.add(highLightedFlowId);
                }

            }

        }
        return highLightedFlowIds;
    }

}
