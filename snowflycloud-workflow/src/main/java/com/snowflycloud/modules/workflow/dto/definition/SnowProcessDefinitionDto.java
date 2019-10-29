package com.snowflycloud.modules.workflow.dto.definition;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName SnowProcessDefinitionDto
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/29 15:19
 * @Version 1.0
 **/
@Data
public class SnowProcessDefinitionDto implements Serializable {
    private static final long serialVersionUID = 345554125093745295L;

    private String id;

    private String name;

    private String key;

    private String deploymentId;

    private Integer version;

    private String resourceName;

    private String diagramResourceName;

    private boolean suspended;

    private String description;

    private String tenantId;

    private String category;

    private Date deploymentTime;
}
