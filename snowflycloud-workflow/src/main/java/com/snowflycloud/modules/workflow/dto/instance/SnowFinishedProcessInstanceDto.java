package com.snowflycloud.modules.workflow.dto.instance;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName SnowFinishedProcessInstanceDto
 * @Description 结束的流程.
 * @Author lixuefei
 * @Date 2019/11/1 16:26
 * @Version 1.0
 **/
@Data
public class SnowFinishedProcessInstanceDto implements Serializable {
    private static final long serialVersionUID = -3104474076452153101L;

    private String processInstanceId;

    private String processDefinitionId;

    private String processDefinitionKey;

    private String processDefinitionName;

    private Integer processDefinitionVersion;

    private String deploymentId;

    private String activityId;

    private String result;

    private String reasons;

    private String applicant;

    private String businessKey;

    private String tenantId;

    private String title;

    private String detailAddress;

    private Date createTime;

    private Date endTime;

    private Long duration;
}
