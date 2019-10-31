package com.snowflycloud.modules.workflow.dto.instance;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName SnowProcessInstanceDto
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/31 20:28
 * @Version 1.0
 **/
@Data
public class SnowProcessInstanceDto implements Serializable {

    private static final long serialVersionUID = -6788895710456379648L;

    private String processInstanceId;

    private String processDefinitionId;

    private String processDefinitionKey;

    private String processDefinitionName;

    private Integer processDefinitionVersion;

    private String deploymentId;

    private String activityId;

    private String currentTaskName;

    private String currentAssignee;

    private String applicant;

    private String businessKey;

    private String status;

    private String tenantId;

    private String title;

    private String detailAddress;

    private Date createTime;
}
