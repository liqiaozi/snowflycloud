package com.snowflycloud.modules.workflow.dto.instance;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName InstanceSearchDto
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/31 19:35
 * @Version 1.0
 **/
@Data
public class InstanceSearchDto implements Serializable {
    private static final long serialVersionUID = 7657974577606621557L;

    private String processInstanceId;

    private String processInstanceName;

    private String processDefinitionKey;

    private String category;

    private String status;

}
