package com.snowflycloud.modules.workflow.dto.instance;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName StartProcessInstanceDto
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/30 21:40
 * @Version 1.0
 **/
@Data
public class StartProcessInstanceDto implements Serializable {
    private static final long serialVersionUID = -3894164770331190115L;

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /**
     * 申请人.
     */
    private String applicant;

    /**
     * 标题.
     */
    private String title;

    /**
     * 流程详情地址.
     */
    private String detailAddress;

    /**
     * 流程变量
     */
    private Map<String, Object> variables;

}
